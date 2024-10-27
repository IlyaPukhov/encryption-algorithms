package com.puhovin.encryption.util

import java.util.Base64
import kotlin.text.Charsets.UTF_8
import org.springframework.stereotype.Service

/**
 * Сервис, ответственный за кодирование и декодирование RSA-ключей.
 */
@Service
class KeyEncoderDecoder(private val messageService: MessageService) {

    /**
     * Кодирование ключа в Base64-строку в формате "число,число".
     *
     * @param key пара чисел, представляющая ключ
     * @return Закодированная Base64-строка
     */
    fun encodeKey(key: Pair<Long, Long>): String {
        return Base64.getEncoder().encodeToString("${key.first},${key.second}".toByteArray())
    }

    /**
     * Декодирование Base64-строки в формате "число,число" в ключ.
     *
     * @param base64String закодированная Base64-строка
     * @return Пара чисел, представляющая ключ
     */
    fun decodeKey(base64String: String): Pair<Long, Long> {
        val decodedText = String(Base64.getDecoder().decode(base64String), UTF_8)

        if (!isValidFormat(decodedText)) {
            throw IllegalArgumentException(messageService.getMessage("error.rsa-encrypt.key.is.invalid"))
        }

        val (first, second) = decodedText.split(",").map { it.toLong() }
        return Pair(first, second)
    }

    /**
     * Проверка формата строки на соответствие шаблону "число,число".
     *
     * @param string проверяемая строка
     * @return true, если строка соответствует шаблону, иначе false
     */
    private fun isValidFormat(string: String): Boolean {
        val regex = Regex("^\\d+,\\d+$")
        return regex.matches(string)
    }

}