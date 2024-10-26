package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.util.MessageService
import org.springframework.stereotype.Service

@Service
class CaesarCipherService(
    private val messageService: MessageService
) : CipherService {

    private val upperAlphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    private val lowerAlphabet = upperAlphabet.lowercase()
    private val alphabetLength = upperAlphabet.length

    override fun encrypt(rawMessage: String, key: String?): String {
        key ?: throw IllegalArgumentException(messageService.getMessage("error.encrypt.key.is.required"))
        return processMessage(rawMessage, key.toInt())
    }

    override fun decrypt(encryptedMessage: String, key: String?): String {
        key ?: throw IllegalArgumentException(messageService.getMessage("error.decrypt.key.is.required"))
        return processMessage(encryptedMessage, -key.toInt())
    }

    private fun processMessage(message: String, key: Int): String {
        val shiftedMessage = StringBuilder()

        for (char in message) {
            when (char) {
                in upperAlphabet -> shiftedMessage.append(shiftChar(char, key, upperAlphabet))
                in lowerAlphabet -> shiftedMessage.append(shiftChar(char, key, lowerAlphabet))
                else -> shiftedMessage.append(char)
            }
        }
        return shiftedMessage.toString()
    }

    fun shiftChar(char: Char, key: Int, alphabet: String): Char {
        val index = alphabet.indexOf(char)
        val shiftedIndex = (index + key + alphabetLength) % alphabetLength

        return alphabet[shiftedIndex]
    }

}