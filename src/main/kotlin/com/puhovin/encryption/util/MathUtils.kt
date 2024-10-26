package com.puhovin.encryption.util

object MathUtils {

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

}
