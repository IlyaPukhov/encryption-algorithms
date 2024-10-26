package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.util.KeyEncoderDecoder
import com.puhovin.encryption.util.MathUtils
import org.springframework.stereotype.Service

/**
 * Сервис для шифрования и дешифрования сообщений методом RSA.
 */
@Service
class RsaCipherService(private val keyDecoder: KeyEncoderDecoder) : CipherService {

    private companion object {
        private const val SPECIAL_CHARACTERS = " ,.!?;:'\"()[]{}<>"
        private const val DELIMITER = '-'
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
            if (char in SPECIAL_CHARACTERS) {
                encryptedMessage.append(char)
                continue
            }

            encryptCharacter(char, e, n)?.let { encryptedMessage.append(it).append(DELIMITER) }
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
     * @return Зашифрованный символ или null, если символ не может быть зашифрован
     */
    fun encryptCharacter(char: Char, e: Long, n: Long): Long? {
        val m = when (char) {
            in 'А'..'Я' -> (char.code - 'А'.code + 1)
            in 'а'..'я' -> (char.code - 'а'.code + 34)
            else -> return null
        }
        return MathUtils.modularExponentiation(m.toLong(), e, n)
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

            if (part in SPECIAL_CHARACTERS) {
                decryptedMessage.append(part)
            } else {
                decryptCharacter(part.toLong(), d, n)?.let { decryptedMessage.append(it) }
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
     * @return Расшифрованный символ или null, если символ не может быть расшифрован
     */
    fun decryptCharacter(c: Long, d: Long, n: Long): Char? {
        val m = MathUtils.modularExponentiation(c, d, n)
        return when (m) {
            in 1..33 -> (m + 'А'.code - 1)
            in 34..66 -> (m + 'а'.code - 34)
            else -> null
        }?.toInt()?.toChar()
    }

}