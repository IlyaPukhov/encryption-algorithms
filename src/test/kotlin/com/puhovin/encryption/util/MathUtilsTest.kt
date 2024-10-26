package com.puhovin.encryption.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathUtilsTest {

    private companion object {
        const val E = 7L
        const val PHI = 122_760L
    }

    @Test
    fun testGcd() {
        val expectedGcd = 1L

        val result = MathUtils.gcd(E, PHI)

        assertThat(result).isEqualTo(expectedGcd)
    }

    @Test
    fun testModularInverse() {
        val expectedD = 105_223L

        val result = MathUtils.modularInverse(E, PHI)

        assertThat(result).isEqualTo(expectedD)
    }

    @Test
    fun testModularExponentiation() {
        val base = 2L
        val exp = 199L
        val mod = 1003L
        val expectedResult = 247L

        val result = MathUtils.modularExponentiation(base, exp, mod)

        assertThat(result).isEqualTo(expectedResult)
    }

}