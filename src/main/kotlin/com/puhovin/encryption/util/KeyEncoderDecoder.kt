package com.puhovin.encryption.util

import java.util.Base64
import org.springframework.stereotype.Service

@Service
class KeyEncoderDecoder(
    private val messageService: MessageService
) {

    fun encodeKey(key: Pair<Long, Long>): String {
        return Base64.getEncoder().encodeToString("${key.first},${key.second}".toByteArray())
    }

    fun decodeKey(base64String: String?): Pair<Long, Long> {
        val decodedText = Base64.getDecoder().decode(base64String).toString()

        if (!isValidFormat(decodedText)) {
            throw IllegalArgumentException(messageService.getMessage("error.rsa-encrypt.key.is.invalid"))
        }

        val (first, second) = decodedText.split(",").map { it.toLong() }
        return Pair(first, second)
    }

    private fun isValidFormat(string: String): Boolean {
        val regex = Regex("^\\d+,\\d+$") // формат "число,число"
        return regex.matches(string)
    }

}