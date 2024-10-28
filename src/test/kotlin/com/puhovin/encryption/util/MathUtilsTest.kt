package com.puhovin.encryption.util

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class MathUtilsTest {

    private companion object {
        const val E = 65537L
        const val PHI = 122_760L
    }

    @Test
    fun gcd() {
        val expectedGcd = 1L

        val result = MathUtils.gcd(E, PHI)

        assertThat(result).isEqualTo(expectedGcd)
    }

    @Test
    fun modularInverse_validInput_returnsD() {
        val expectedD = 99593L

        val result = MathUtils.calculateModInverse(E, PHI)

        assertThat(result).isEqualTo(expectedD)
    }

    @Test
    fun modularInverse_invalidInput_throwsException() {
        val invalidE = 0L

        assertThatThrownBy { MathUtils.calculateModInverse(invalidE, PHI) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("e и φ должны быть больше нуля!")
    }

    @Test
    fun modularExponentiation() {
        val base = 2L
        val exp = 199L
        val mod = 1003L
        val expectedResult = 247L

        val result = MathUtils.modularExponentiation(base, exp, mod)

        assertThat(result).isEqualTo(expectedResult)
    }

}