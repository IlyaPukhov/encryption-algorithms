package com.puhovin.encryption.util

/**
 * Утилитный класс для математических операций.
 */
object MathUtils {

    /**
     * Алгоритм Евклида для нахождения наибольшего общего делителя (НОД) двух чисел.
     *
     * @param a первое число
     * @param b второе число
     * @return НОД чисел a и b
     */
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

    /**
     * Алгоритм нахождения мультипликативной обратной по модулю на основе расширенного алгоритма Евклида.
     * Находит такое целое число d, что произведение de по модулю phi равно 1
     *
     * @param e число, для которого находится обратная
     * @param phi модуль
     * @return Мультипликативная обратная d по модулю phi
     */
    fun calculateModInverse(e: Long, phi: Long): Long {
        if (e <= 0) throw IllegalArgumentException("e и φ должны быть больше нуля!")

        var a = phi
        var y = 0L
        var x = 1L

        var b = e % phi

        if (b == 0L) return 0

        while (b > 1) {
            val q = b / a
            val t = a

            a = b % a
            b = t
            val tempY = y
            y = x - q * y
            x = tempY
        }

        return if (x < 0) (x + phi) else x
    }

    /**
     * Алгоритм возведения в степень по модулю.
     *
     * @param base основание
     * @param exp показатель степени
     * @param mod модуль
     * @return Результат возведения в степень по модулю
     */
    fun modularExponentiation(base: Long, exp: Long, mod: Long): Long {
        val a0 = base % mod
        var result = a0

        val binaryRepresentation = exp.toUInt().toString(radix = 2)
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