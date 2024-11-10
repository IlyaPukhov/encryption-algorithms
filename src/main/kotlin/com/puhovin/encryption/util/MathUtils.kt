package com.puhovin.encryption.util

import java.math.BigInteger

/**
 * Утилитный класс для математических операций.
 */
object MathUtils {

    /**
     * Алгоритм нахождения мультипликативной обратной по модулю на основе расширенного алгоритма Евклида.
     * Находит такое целое число d, что произведение de по модулю phi равно 1
     *
     * @param e число, для которого находится обратная
     * @param phi модуль
     * @return Мультипликативная обратная d по модулю phi
     */
    fun calculateModularMultiplicativeInverse(e: BigInteger, phi: BigInteger): BigInteger {
        if (e <= BigInteger.ZERO) throw IllegalArgumentException("e и φ должны быть больше нуля!")

        var (a, b) = Pair(phi, e % phi)
        var (x0, x1) = Pair(BigInteger.ZERO, BigInteger.ONE)

        if (b == BigInteger.ZERO) return BigInteger.ZERO

        while (b > BigInteger.ONE) {
            val q = b / a
            var temp = a

            a = b % a
            b = temp
            temp = x0

            x0 = x1 - q * x0
            x1 = temp
        }

        return if (x1 < BigInteger.ZERO) x1 + phi else x1
    }

    /**
     * Алгоритм быстрого возведения в степень по модулю.
     *
     * @param base основание
     * @param exp показатель степени
     * @param mod модуль
     * @return Результат возведения в степень по модулю
     */
    fun modularExponentiation(base: BigInteger, exp: BigInteger, mod: BigInteger): BigInteger {
        val a0 = base % mod
        var result = a0

        val binaryRepresentation = exp.toString(2)
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