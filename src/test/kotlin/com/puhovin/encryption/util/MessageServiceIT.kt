package com.puhovin.encryption.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.context.i18n.LocaleContextHolder

@SpringBootTest
class MessageServiceIT(private val messageService: MessageService) {

    @SpyBean
    private lateinit var messageSource: MessageSource

    @Test
    fun getMessage_existsMessage_returnsMessageString() {
        val messageCode = "test.message"
        val expectedMessage = "Тестовое сообщение"

        val message = messageService.getMessage(messageCode)

        assertThat(message).isEqualTo(expectedMessage)
        verify(messageSource, times(1)).getMessage(eq(messageCode), any(), any())
    }

    @Test
    fun getMessage_nonExistsMessage_throwsException() {
        val messageCode = "code"

        assertThatThrownBy { messageService.getMessage(messageCode) }
            .isInstanceOf(NoSuchMessageException::class.java)
            .hasMessage(
                "No message found under code '%s' for locale '%s'.".format(messageCode, LocaleContextHolder.getLocale())
            )
        verify(messageSource, times(1)).getMessage(eq(messageCode), any(), any())
    }

}