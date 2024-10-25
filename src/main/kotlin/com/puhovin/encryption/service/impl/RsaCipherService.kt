package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.util.KeyDecoder
import kotlin.math.pow
import org.springframework.stereotype.Service

@Service
class RsaCipherService(private val keyDecoder: KeyDecoder) : CipherService {

    private companion object {
        private const val SPECIAL_CHARACTERS = " ,.!?;:'\"()[]{}<>"
        private const val DELIMITER = '-'
    }

    override fun encrypt(rawMessage: String, key: String?): String {
        val (e, n) = keyDecoder.getKeyFromBase64(key)
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

    fun encryptCharacter(char: Char, e: Int, n: Int): Int? {
        return when (char) {
            in 'А'..'Я' -> (char.code - 'А'.code + 1).toDouble().pow(e).toInt() % n
            in 'а'..'я' -> (char.code - 'а'.code + 34).toDouble().pow(e).toInt() % n
            else -> null
        }
    }

    override fun decrypt(encryptedMessage: String, key: String?): String {
        val (d, n) = keyDecoder.getKeyFromBase64(key)
        val decryptedMessage = StringBuilder()

        val encryptedParts = encryptedMessage.split(DELIMITER)

        for (part in encryptedParts) {
            if (part.isBlank()) continue

            if (part in SPECIAL_CHARACTERS) decryptedMessage.append(part)

            decryptCharacter(part.toInt(), d, n)?.let {
                decryptedMessage.append(it)
            }
        }

        return decryptedMessage.toString()
    }

    fun decryptCharacter(encryptedValue: Int, d: Int, n: Int): Char? {
        val decryptedInt = (encryptedValue.toDouble().pow(d).toInt() % n)
        return when (decryptedInt) {
            in 1..33 -> (decryptedInt + 'А'.code - 1).toChar()
            in 34..66 -> (decryptedInt + 'а'.code - 34).toChar()
            else -> null
        }
    }

}