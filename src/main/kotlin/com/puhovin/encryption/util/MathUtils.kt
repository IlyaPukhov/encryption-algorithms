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
    fun calculateModularMultiplicativeInverse(e: Long, phi: Long): Long {
        if (e <= 0) throw IllegalArgumentException("e и φ должны быть больше нуля!")

        var (a, b) = Pair(phi, e % phi)
        var (x0, x1) = Pair(0L, 1L)

        if (b == 0L) return 0

        while (b > 1) {
            val q = b / a
            var temp = a

            a = b % a
            b = temp
            temp = x0

            x0 = x1 - q * x0
            x1 = temp
        }

        return if (x1 < 0) (x1 + phi) else x1
    }

    /**
     * Алгоритм быстрого возведения в степень по модулю.
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

    /**
     * Проверяет, является ли заданное число простым.
     *
     * Простое число - это натуральное число, большее 1, которое не имеет положительных делителей, кроме 1 и самого себя.
     *
     * @param number число, которое нужно проверить на простоту
     * @return true, если число простое, иначе false
     */
    fun isPrime(number: Long): Boolean {
        if (number <= 1) return false
        if (number <= 3) return true
        if (number % 2 == 0L || number % 3 == 0L) return false

        var i = 5L
        while (i * i <= number) {
            if (number % i == 0L) return false
            i += 2
        }
        return true
    }

}