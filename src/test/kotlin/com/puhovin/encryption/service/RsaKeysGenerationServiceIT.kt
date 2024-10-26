package com.puhovin.encryption.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RsaKeysGenerationServiceIT(private val keysGenerationService: RsaKeysGenerationService) {

    private companion object {
        const val PUBLIC_KEY = "NywxMjM0Njc="
        const val PRIVATE_KEY = "MTA1MjIzLDEyMzQ2Nw=="
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