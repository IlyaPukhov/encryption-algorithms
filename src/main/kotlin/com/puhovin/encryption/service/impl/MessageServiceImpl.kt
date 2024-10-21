package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.MessageService
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class MessageServiceImpl(private val messageSource: MessageSource) : MessageService {

    override fun getMessage(code: String): String = messageSource.getMessage(code, null, Locale.getDefault())

}