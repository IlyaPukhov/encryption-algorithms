package com.puhovin.encryption.service

import com.puhovin.encryption.service.impl.DiffieHellmanProtocolServiceImpl
import java.math.BigInteger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DiffieHellmanProtocolServiceTest {

    private val diffieHellmanProtocolService = DiffieHellmanProtocolServiceImpl()

    private companion object {
        const val CERTAINTY = 100
    }

    @BeforeEach
    fun setUp() {
        diffieHellmanProtocolService.init()
    }

    @Test
    fun generatePrivateKey_returnsPrimeNumber() {
        val privateKey = diffieHellmanProtocolService.generatePrivateKey()

        assertThat(privateKey.isProbablePrime(CERTAINTY)).isTrue
    }

    @Test
    fun generatePublicKey_returnsCorrectKey() {
        val privateKey = diffieHellmanProtocolService.generatePrivateKey()
        val publicKey = diffieHellmanProtocolService.generatePublicKey(privateKey)

        assertThat(publicKey).isGreaterThan(BigInteger.ZERO)
    }

    @Test
    fun generateSharedSecret_returnsCorrectKey() {
        val privateKey = diffieHellmanProtocolService.generatePrivateKey()
        val otherPartyPublicKey = diffieHellmanProtocolService.generatePublicKey(privateKey)

        val sharedSecret = diffieHellmanProtocolService.generateSharedSecret(otherPartyPublicKey, privateKey)

        assertThat(sharedSecret).isGreaterThan(BigInteger.ZERO)
    }

    @Test
    fun init() {
        assertThat(diffieHellmanProtocolService.w.isProbablePrime(CERTAINTY)).isTrue
        assertThat(diffieHellmanProtocolService.n.isProbablePrime(CERTAINTY)).isTrue
    }

}
