package com.puhovin.encryption.util

import java.math.BigInteger

/**
 * Утилитный класс для математических операций.
 */
object MathUtils {

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