package com.puhovin.encryption.service

import com.puhovin.encryption.util.KeyEncoderDecoder
import com.puhovin.encryption.util.MathUtils
import com.puhovin.encryption.util.MessageService
import jakarta.annotation.PostConstruct
import kotlin.properties.Delegates
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RsaKeyGeneratorService(
    private val keyEncoder: KeyEncoderDecoder,
    private val messageService: MessageService
) {

    @Value("\${encrypt.rsa.p}")
    private var p: Long = 0

    @Value("\${encrypt.rsa.q}")
    private var q: Long = 0

    private var n by Delegates.notNull<Long>()
    private var e by Delegates.notNull<Long>()
    private var d by Delegates.notNull<Long>()

    @PostConstruct
    fun init() {
        if (p == 0L || q == 0L) {
            throw IllegalStateException(messageService.getMessage("error.rsa-encrypt.pq-is-not-initialized"))
        }

        n = p * q
        val phi = (p - 1) * (q - 1)
        e = findPublicExponent(phi)
        d = calculatePrivateD(e, phi)
    }

    fun getPublicKey() = keyEncoder.encodeKey(Pair(e, n))

    fun getPrivateKey() = keyEncoder.encodeKey(Pair(d, n))

    private fun findPublicExponent(phi: Long): Long {
        var e = 3L
        while (MathUtils.gcd(e, phi) != 1L) {
            e += 2
        }
        return e
    }

    private fun calculatePrivateD(e: Long, phi: Long) = MathUtils.modularInverse(e, phi)

}