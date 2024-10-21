package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.CipherService
import org.springframework.stereotype.Service

@Service
class CaesarCipherService : CipherService {

    private val upperAlphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    private val lowerAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
    private val alphabetLength = upperAlphabet.length

    override fun encrypt(rawMessage: String, key: Int): String {
        return processMessage(rawMessage, key)
    }

    override fun decrypt(encryptedMessage: String, key: Int): String {
        return processMessage(encryptedMessage, -key)
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
        return if (index != -1) {
            val shiftedIndex = (index + key + alphabetLength) % alphabetLength
            alphabet[shiftedIndex]
        } else char
    }

}
