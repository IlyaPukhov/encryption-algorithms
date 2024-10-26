package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.CipherService
import org.springframework.stereotype.Service

/**
 * Сервис, реализующий шифрование и дешифрование методом Цезаря.
 *
 * Этот сервис позволяет шифровать и дешифровать сообщения, используя ключ, заданный пользователем.
 */
@Service
class CaesarCipherService : CipherService {

    private val upperAlphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    private val lowerAlphabet = upperAlphabet.lowercase()
    private val alphabetLength = upperAlphabet.length

    /**
     * Шифрует сообщение методом Цезаря.
     *
     * @param rawMessage исходное сообщение
     * @param key ключ шифрования
     * @return Зашифрованное сообщение
     */
    override fun encrypt(rawMessage: String, key: String): String {
        return processMessage(rawMessage, key.toInt())
    }

    /**
     * Дешифрует зашифрованное сообщение методом Цезаря.
     *
     * @param encryptedMessage зашифрованное сообщение
     * @param key ключ дешифрования
     * @return Дешифрованное сообщение
     */
    override fun decrypt(encryptedMessage: String, key: String): String {
        return processMessage(encryptedMessage, -key.toInt())
    }

    /**
     * Обрабатывает сообщение, сдвигая каждый символ на заданное количество позиций в рамках алфавита.
     *
     * @param message сообщение для обработки
     * @param key ключ сдвига
     * @return Обработанное сообщение
     */
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

    /**
     * Сдвигает символ на заданное количество позиций в алфавите.
     *
     * @param char символ для сдвига
     * @param key ключ сдвига
     * @param alphabet алфавит, в котором находится символ
     * @return Сдвинутый символ
     */
    private fun shiftChar(char: Char, key: Int, alphabet: String): Char {
        val index = alphabet.indexOf(char)
        val shiftedIndex = (index + key + alphabetLength) % alphabetLength

        return alphabet[shiftedIndex]
    }

}