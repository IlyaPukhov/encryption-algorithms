package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.util.KeyEncoderDecoder
import com.puhovin.encryption.util.MathUtils
import com.puhovin.encryption.util.MessageService
import java.math.BigInteger
import org.springframework.stereotype.Service

/**
 * Сервис для шифрования и дешифрования сообщений методом RSA.
 */
@Service
class RsaCipherService(
    private val keyDecoder: KeyEncoderDecoder,
    private val messageService: MessageService
) : CipherService {

    private companion object {
        const val UPPER_START = 'А'
        const val LOWER_START = 'а'
        const val BANNED_CHARACTERS = "123456789-"
        const val DELIMITER = '-'
    }

    /**
     * Шифрует сообщение с помощью ключа.
     *
     * @param rawMessage исходное сообщение
     * @param key ключ для шифрования
     * @return Зашифрованное сообщение
     */
    override fun encrypt(rawMessage: String, key: String): String {
        val (e, n) = keyDecoder.decodeKey(key)
        val encryptedMessage = StringBuilder()

        for (char in rawMessage) {
            if (char in BANNED_CHARACTERS) throw IllegalArgumentException(messageService.getMessage("error.rsa-encrypt.message-is-invalid"))

            if (char.lowercaseChar() in 'а'..'я') {
                encryptCharacter(char, e, n).let { encryptedMessage.append(it).append(DELIMITER) }
            } else {
                encryptedMessage.append(char)
            }
        }

        if (encryptedMessage.isNotEmpty() && encryptedMessage.last() == DELIMITER) {
            encryptedMessage.deleteCharAt(encryptedMessage.length - 1)
        }

        return encryptedMessage.toString()
    }

    /**
     * Шифрует один символ с помощью ключа.
     *
     * @param char символ для шифрования
     * @param e открытая экспонента ключа
     * @param n модуль ключа
     * @return Зашифрованный символ или исключение, если символ не может быть зашифрован
     */
    private fun encryptCharacter(char: Char, e: BigInteger, n: BigInteger): BigInteger {
        val m = when (char) {
            in 'а'..'я' -> (char.code - LOWER_START.code + 1) + 1
            in 'А'..'Я' -> (char.code - UPPER_START.code + 34) + 1
            else -> throw IllegalArgumentException(messageService.getMessage("error.rsa-encrypt.message-is-invalid"))
        }
        return MathUtils.modularExponentiation(m.toBigInteger(), e, n)
    }

    /**
     * Расшифровывает сообщение с помощью ключа.
     *
     * @param encryptedMessage зашифрованное сообщение
     * @param key ключ для дешифрования
     * @return Расшифрованное сообщение
     */
    override fun decrypt(encryptedMessage: String, key: String): String {
        val (d, n) = keyDecoder.decodeKey(key)
        val decryptedMessage = StringBuilder()

        val encryptedParts = encryptedMessage.split(DELIMITER)

        for (part in encryptedParts) {
            if (part.isBlank()) continue

            if (part.toBigIntegerOrNull() != null) {
                decryptCharacter(part.toBigInteger(), d, n).let { decryptedMessage.append(it) }
            } else {
                decryptedMessage.append(part)
            }
        }

        return decryptedMessage.toString()
    }

    /**
     * Расшифровывает один символ с помощью ключа.
     *
     * @param c зашифрованный символ
     * @param d секретная экспонента ключа
     * @param n модуль ключа
     * @return Расшифрованный символ или исключение, если символ не может быть расшифрован
     */
    private fun decryptCharacter(c: BigInteger, d: BigInteger, n: BigInteger): Char {
        val m = MathUtils.modularExponentiation(c, d, n)
        return when (m.toInt()) {
            in 1..33 -> (m.toInt() + LOWER_START.code - 1) - 1
            in 34..66 -> (m.toInt() + UPPER_START.code - 34) - 1
            else -> throw IllegalArgumentException(messageService.getMessage("error.rsa-encrypt.key-message-is-invalid"))
        }.toInt().toChar()
    }

}