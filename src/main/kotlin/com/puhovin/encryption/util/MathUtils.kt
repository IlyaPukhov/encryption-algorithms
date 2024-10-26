package com.puhovin.encryption.util

object MathUtils {

    // Алгоритм нахождения наибольшего общего делителя
    fun gcd(a: Long, b: Long): Long {
        var x = a
        var y = b

        while (y != 0L) {
            val temp = y
            y = x % y
            x = temp
        }
        return x
    }

    // Алгоритм нахождения мультипликативной обратной по модулю
    fun modularInverse(e: Long, phi: Long): Long {
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

    // Алгоритм возведения в степень по модулю
    fun modularExponentiation(base: Long, degree: Long, mod: Long): Long {
        val a0 = base % mod
        var result = a0

        val binaryRepresentation = degree.toUInt().toString(radix = 2)
        val bPayload = binaryRepresentation.drop(1)

        for (bi in bPayload) {
            result = if (bi == '1') {
                (result * result * a0) % mod
            } else {
                (result * result) % mod
            }
        }

        return result
    }

}
