package com.puhovin.encryption.service

import com.puhovin.encryption.service.impl.CaesarCipherService
import com.puhovin.encryption.service.impl.MessageServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CaesarCipherServiceTest {

    @Mock
    private lateinit var messageService: MessageServiceImpl

    @InjectMocks
    private lateinit var caesarCipherService: CaesarCipherService

    @Test
    fun encrypt_keyIsSpecified_encryptMessage() {
        val key = 3
        val rawMessage = "Привет, мир!"
        val expectedMessage = "Тулезх, плу!"

        val encryptedMessage = caesarCipherService.encrypt(rawMessage, key)

        assertThat(expectedMessage).isEqualTo(encryptedMessage)
    }

    @Test
    fun encrypt_keyIsNotSpecified_throwsException() {
        val rawMessage = "Привет, мир!"
        val key: Int? = null

        assertThatThrownBy { caesarCipherService.encrypt(rawMessage, key) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun decrypt_keyIsSpecified_decryptEncryptedMessage() {
        val key = 3
        val encryptedMessage = "Тулезх, плу!"

        val decryptedMessage = caesarCipherService.decrypt(encryptedMessage, key)

        val expectedMessage = "Привет, мир!"
        assertThat(expectedMessage).isEqualTo(decryptedMessage)
    }

    @Test
    fun decrypt_keyIsNotSpecified_throwsException() {
        val encryptedMessage = "Усйиёх, плу!"
        val key: Int? = null

        assertThatThrownBy { caesarCipherService.encrypt(encryptedMessage, key) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }

}