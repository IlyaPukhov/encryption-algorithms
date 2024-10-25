package com.puhovin.encryption.util

import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class MessageService(private val messageSource: MessageSource) {

    fun getMessage(code: String): String = messageSource.getMessage(code, null, Locale.getDefault())

}