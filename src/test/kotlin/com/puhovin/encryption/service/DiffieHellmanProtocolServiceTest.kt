package com.puhovin.encryption.service

import com.puhovin.encryption.service.impl.DiffieHellmanProtocolServiceImpl
import com.puhovin.encryption.util.MathUtils
import kotlin.random.Random
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DiffieHellmanProtocolServiceTest {

    private val minNumber: Long = 100
    private val maxNumber: Long = 200

    private lateinit var diffieHellmanProtocolService: DiffieHellmanProtocolServiceImpl

    @BeforeEach
    fun setUp() {
        diffieHellmanProtocolService = DiffieHellmanProtocolServiceImpl().apply {
            w = Random.nextLong(minNumber, maxNumber)
            n = Random.nextLong(minNumber, maxNumber)
        }
    }

    @Test
    fun generatePrivateKey_returnsPrimeNumber() {
        val privateKey = diffieHellmanProtocolService.generatePrivateKey()

        assertThat(MathUtils.isPrime(privateKey)).isTrue
    }

    @Test
    fun generatePublicKey_returnsCorrectKey() {
        val privateKey = diffieHellmanProtocolService.generatePrivateKey()
        val publicKey = diffieHellmanProtocolService.generatePublicKey(privateKey)

        assertThat(publicKey).isGreaterThan(0)
    }

    @Test
    fun generateSharedSecret_returnsCorrectKey() {
        val privateKey = diffieHellmanProtocolService.generatePrivateKey()
        val otherPartyPublicKey = diffieHellmanProtocolService.generatePublicKey(privateKey)

        val sharedSecret = diffieHellmanProtocolService.generateSharedSecret(otherPartyPublicKey, privateKey)

        assertThat(sharedSecret).isGreaterThan(0)
    }

    @Test
    fun init() {
        diffieHellmanProtocolService.init()

        assertThat(MathUtils.isPrime(diffieHellmanProtocolService.w)).isTrue
        assertThat(MathUtils.isPrime(diffieHellmanProtocolService.n)).isTrue
    }

}
