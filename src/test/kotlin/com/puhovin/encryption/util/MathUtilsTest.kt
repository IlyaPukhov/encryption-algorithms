package com.puhovin.encryption.util

import java.math.BigInteger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathUtilsTest {

    private companion object {
        val E: BigInteger = 65537.toBigInteger()
        val PHI: BigInteger = 122_760.toBigInteger()
    }

    @Test
    fun gcd() {
        val expectedGcd = 1L

        val result = E.gcd(PHI)

        assertThat(result).isEqualTo(expectedGcd)
    }

    @Test
    fun modularExponentiation() {
        val base = BigInteger.TWO
        val exp = 199.toBigInteger()
        val mod = 1003.toBigInteger()
        val expectedResult = 247.toBigInteger()

        val result = MathUtils.modularExponentiation(base, exp, mod)

        assertThat(result).isEqualTo(expectedResult)
    }

}