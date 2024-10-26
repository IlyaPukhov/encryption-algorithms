package com.puhovin.encryption.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathUtilsTest {

    @Test
    fun testGcd() {
        val result = MathUtils.gcd(199L, 1003L) // todo дописать после ручных расчетов

        assertThat(result).isEqualTo(247L)
    }

    @Test
    fun testModularInverse() {
        val result = MathUtils.modularInverse(199L, 1003L) // todo дописать после ручных расчетов

        assertThat(result).isEqualTo(247L)
    }

    @Test
    fun testModularExponentiation() {
        val result = MathUtils.modularExponentiation(2L, 199L, 1003L)

        assertThat(result).isEqualTo(247L)
    }

}