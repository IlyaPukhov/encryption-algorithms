package com.puhovin.encryption.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RsaKeysGenerationServiceIT(private val keysGenerationService: RsaKeysGenerationService) {

    private companion object {
        const val PUBLIC_KEY = "NjU1MzcsMTIzNDY3"
        const val PRIVATE_KEY = "OTk1OTMsMTIzNDY3"
    }

    @Test
    fun getPublicKey() {
        val publicKey = keysGenerationService.getPublicKey()

        assertThat(publicKey).isEqualTo(PUBLIC_KEY)
    }

    @Test
    fun getPrivateKey() {
        val privateKey = keysGenerationService.getPrivateKey()

        assertThat(privateKey).isEqualTo(PRIVATE_KEY)
    }

}