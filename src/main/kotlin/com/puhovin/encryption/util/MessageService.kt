package com.puhovin.encryption.util

import java.util.Locale
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service

@Service
class MessageService(private val messageSource: MessageSource) {

    fun getMessage(code: String): String = messageSource.getMessage(code, null, Locale.getDefault())

}