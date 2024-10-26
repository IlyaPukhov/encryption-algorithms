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
     * Самописный алгоритм нахождения мультипликативной обратной по модулю.
     * Находит такое целое число d, что произведение de по модулю phi равно 1
     *
     * @param e число, для которого находится обратная
     * @param phi модуль
     * @return Мультипликативная обратная d по модулю phi
     */
    fun modularInverse(e: Long, phi: Long): Long {
        if (e <= 0 || phi <= 1) throw IllegalArgumentException("e и φ должны быть больше нуля!")

        var k = 1L
        var expression: Long
        do {
            expression = (phi * k + 1)
            k++
        } while (expression % e != 0L)

        return expression / e
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