package com.puhovin.encryption.util

import java.util.Base64
import org.springframework.stereotype.Service

@Service
class KeyDecoder(private val messageService: MessageService) {

    fun getKeyFromBase64(base64String: String?): Pair<Int, Int> {
        val decodedText = Base64.getDecoder().decode(base64String).toString()

        if (!isValidFormat(decodedText)) {
            throw IllegalArgumentException(messageService.getMessage("error.rsa-encrypt.key.is.invalid"))
        }

        val (first, second) = decodedText.split(",").map { it.toInt() }
        return Pair(first, second)
    }

    private fun isValidFormat(string: String): Boolean {
        val regex = Regex("^\\d+,\\d+$") // формат "число,число"
        return regex.matches(string)
    }

}