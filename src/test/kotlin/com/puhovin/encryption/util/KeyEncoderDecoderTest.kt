package com.puhovin.encryption.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class KeyEncoderDecoderTest {

    private companion object {
        val RAW_KEY = Pair(123456L, 7891011L)
        const val ENCODED_KEY = "MTIzNDU2LDc4OTEwMTE="
    }

    @Mock
    private lateinit var messageService: MessageService

    @InjectMocks
    private lateinit var keyEncoderDecoder: KeyEncoderDecoder

    @Test
    fun encodeKey() {
        val encodedKey = keyEncoderDecoder.encodeKey(RAW_KEY)

        assertThat(encodedKey).isEqualTo(ENCODED_KEY)
    }

    @Test
    fun decodeKey_validInput_returnsDecodedKey() {
        val decodedKey = keyEncoderDecoder.decodeKey(ENCODED_KEY)

        assertThat(decodedKey.first).isEqualTo(RAW_KEY.first)
        assertThat(decodedKey.second).isEqualTo(RAW_KEY.second)
    }

    @Test
    fun decodeKey_invalidInputFormat_throwsException() {
        val encodedString = "invalid_base64_string"

        assertThatThrownBy { keyEncoderDecoder.decodeKey(encodedString) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

}