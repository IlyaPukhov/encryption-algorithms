package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.util.KeyEncoderDecoder
import org.springframework.stereotype.Service

@Service
class RsaCipherService(
    private val keyDecoder: KeyEncoderDecoder
) : CipherService {

    private companion object {
        private const val SPECIAL_CHARACTERS = " ,.!?;:'\"()[]{}<>"
        private const val DELIMITER = '-'
    }

    override fun encrypt(rawMessage: String, key: String?): String {
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
            in 'А'..'Я' -> (char.code - 'А'.code + 1).toDouble()
            in 'а'..'я' -> (char.code - 'а'.code + 34).toDouble()
            else -> null
        }
        return null // TODO c=m^e mod n
    }

    override fun decrypt(encryptedMessage: String, key: String?): String {
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
        val m = 1.toInt() // TODO m=c^d mod n
        return when (m) {
            in 1..33 -> (m + 'А'.code - 1).toChar()
            in 34..66 -> (m + 'а'.code - 34).toChar()
            else -> null
        }
    }

}