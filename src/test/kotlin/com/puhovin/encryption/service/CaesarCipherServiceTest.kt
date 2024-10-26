package com.puhovin.encryption.service

import com.puhovin.encryption.service.impl.CaesarCipherService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class CaesarCipherServiceTest {

    private companion object {
        const val RAW_MESSAGE = "Пух!"
        const val ENCRYPTED_MESSAGE = "Тцш!"
    }

    private val caesarCipherService = CaesarCipherService()

    @Test
    fun encrypt_keyIsSpecified_encryptMessage() {
        val key = 3

        val encryptedResult = caesarCipherService.encrypt(RAW_MESSAGE, key.toString())

        assertThat(encryptedResult).isEqualTo(ENCRYPTED_MESSAGE)
    }

    @Test
    fun encrypt_keyIsNotSpecified_throwsException() {
        val key: Int? = null

        assertThatThrownBy { caesarCipherService.encrypt(RAW_MESSAGE, key.toString()) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun decrypt_keyIsSpecified_decryptEncryptedMessage() {
        val key = 3
        val expectedMessage = RAW_MESSAGE

        val decryptedResult = caesarCipherService.decrypt(ENCRYPTED_MESSAGE, key.toString())

        assertThat(decryptedResult).isEqualTo(expectedMessage)
    }

    @Test
    fun decrypt_keyIsNotSpecified_throwsException() {
        val key: Int? = null

        assertThatThrownBy { caesarCipherService.encrypt(ENCRYPTED_MESSAGE, key.toString()) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

}