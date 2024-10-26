package com.puhovin.encryption.service

import com.puhovin.encryption.service.impl.RsaCipherService
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RsaCipherServiceIT(
    private val rsaCipherService: RsaCipherService,
    private val keysGenerationService: RsaKeysGenerationService
) {

    private companion object {
        const val RAW_MESSAGE = "пух!"
        const val ENCRYPTED_MESSAGE = "82249-16069-102754-!"
    }

    @Test
    fun encrypt() {
        val publicKey = keysGenerationService.getPublicKey()

        val encryptedMessage = rsaCipherService.encrypt(RAW_MESSAGE, publicKey)
        println(encryptedMessage)

        assertThat(encryptedMessage).isEqualTo(ENCRYPTED_MESSAGE)
    }

    @Test
    fun decrypt() {
        val privateKey = keysGenerationService.getPrivateKey()

        val decryptedMessage = rsaCipherService.decrypt(ENCRYPTED_MESSAGE, privateKey)

        assertThat(decryptedMessage).isEqualTo(RAW_MESSAGE)
    }

}