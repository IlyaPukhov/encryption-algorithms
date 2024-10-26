package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.util.KeyEncoderDecoder
import com.puhovin.encryption.util.MathUtils
import com.puhovin.encryption.util.MessageService
import org.springframework.stereotype.Service

@Service
class RsaCipherService(
    private val keyDecoder: KeyEncoderDecoder,
    private val messageService: MessageService
) : CipherService {

    private companion object {
        private const val SPECIAL_CHARACTERS = " ,.!?;:'\"()[]{}<>"
        private const val DELIMITER = '-'
    }

    override fun encrypt(rawMessage: String, key: String?): String {
        key ?: throw IllegalArgumentException(messageService.getMessage("error.encrypt.key.is.required"))

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

    fun encryptCharacter(char: Char, e: Long, n: Long): Long? {
        val m = when (char) {
            in 'А'..'Я' -> (char.code - 'А'.code + 1)
            in 'а'..'я' -> (char.code - 'а'.code + 34)
            else -> return null
        }
        return MathUtils.modularExponentiation(m.toLong(), e, n)
    }

    override fun decrypt(encryptedMessage: String, key: String?): String {
        key ?: throw IllegalArgumentException(messageService.getMessage("error.decrypt.key.is.required"))

        val (d, n) = keyDecoder.decodeKey(key)
        val decryptedMessage = StringBuilder()

        val encryptedParts = encryptedMessage.split(DELIMITER)

        for (part in encryptedParts) {
            if (part.isBlank()) continue

            if (part in SPECIAL_CHARACTERS) decryptedMessage.append(part)

            decryptCharacter(part.toLong(), d, n)?.let {
                decryptedMessage.append(it)
            }
        }

        return decryptedMessage.toString()
    }

    fun decryptCharacter(c: Long, d: Long, n: Long): Char? {
        val m = MathUtils.modularExponentiation(c, d, n)
        return when (m) {
            in 1..33 -> (m + 'А'.code - 1)
            in 34..66 -> (m + 'а'.code - 34)
            else -> null
        }?.toInt()?.toChar()
    }

}