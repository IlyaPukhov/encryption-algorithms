package com.puhovin.encryption.service

import com.puhovin.encryption.util.KeyEncoderDecoder
import com.puhovin.encryption.util.MathUtils
import jakarta.annotation.PostConstruct
import kotlin.properties.Delegates
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RsaKeyGeneratorService(
    private val keyEncoder: KeyEncoderDecoder
) {

    @get:Value("\${encrypt.rsa.p}")
    private var p by Delegates.notNull<Long>()

    @get:Value("\${encrypt.rsa.q}")
    private var q by Delegates.notNull<Long>()

    private var n by Delegates.notNull<Long>()
    private var e by Delegates.notNull<Long>()
    private var d by Delegates.notNull<Long>()

    @PostConstruct
    fun init() {
        n = p * q
        val phi = (p - 1) * (q - 1)
        e = findPublicExponent(phi)
        d = calculatePrivateD(e, phi)
    }

    fun getPublicKey(): String = keyEncoder.encodeKey(Pair(e, n))

    fun getPrivateKey(): String = keyEncoder.encodeKey(Pair(d, n))

    private fun findPublicExponent(phi: Long): Long {
        var e = 3L
        while (MathUtils.gcd(e, phi) != 1L && e < n) {
            e += 2
        }
        return e
    }

    // Функция для нахождения мультипликативной обратной по модулю
    private fun calculatePrivateD(e: Long, phi: Long): Long {
        var m0 = phi
        var y = 0L
        var x = 1L

        if (phi == 1L) return 0

        var eTemp = e
        while (eTemp > 1) {
            val q = eTemp / m0
            val t = m0

            m0 = (eTemp % m0)
            eTemp = t

            val t1 = y
            y = x - q * y
            x = t1
        }

        if (x < 0) x += phi

        return x
    }

}