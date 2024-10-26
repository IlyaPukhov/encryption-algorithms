package com.puhovin.encryption.util

object MathUtils {

    // Функция для нахождения НОД
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

    // Функция для нахождения мультипликативной обратной по модулю
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

}
