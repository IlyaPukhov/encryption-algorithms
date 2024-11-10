package com.puhovin.encryption.service

import com.puhovin.encryption.util.KeyEncoderDecoder
import com.puhovin.encryption.util.MathUtils
import com.puhovin.encryption.util.MessageService
import jakarta.annotation.PostConstruct
import java.math.BigInteger
import kotlin.properties.Delegates
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * Сервис, ответственный за генерацию ключей RSA.
 *
 * Этот сервис генерирует ключи RSA на основе заданных простых чисел p и q.
 */
@Service
class RsaKeysGenerationService(
    private val keyEncoder: KeyEncoderDecoder,
    private val messageService: MessageService,
) {

    private companion object {
        val START_E: BigInteger = 65537.toBigInteger()
    }

    @Value("\${encrypt.rsa.p}")
    private var p: BigInteger = BigInteger.ZERO

    @Value("\${encrypt.rsa.q}")
    private var q: BigInteger = BigInteger.ZERO

    private var n by Delegates.notNull<BigInteger>()
    private var e by Delegates.notNull<BigInteger>()
    private var d by Delegates.notNull<BigInteger>()

    /**
     * Инициализация сервиса.
     *
     * Этот метод вызывается после запуска приложения.
     * Он проверяет, что простые числа p и q заданы, и генерирует ключи RSA единоразово на все время работы приложения.
     */
    @PostConstruct
    fun init() {
        if (p == BigInteger.ZERO || q == BigInteger.ZERO) {
            throw IllegalStateException(messageService.getMessage("error.rsa-encrypt.pq-is-not-initialized"))
        }

        n = p * q
        val phi = (p - BigInteger.ONE) * (q - BigInteger.ONE)
        e = findPublicExponent(phi)
        d = calculatePrivateD(e, phi)
    }

    /**
     * Получение открытого ключа.
     *
     * @return Открытый ключ в закодированном виде
     */
    fun getPublicKey() = keyEncoder.encodeKey(Pair(e, n))

    /**
     * Получение закрытого ключа.
     *
     * @return Закрытый ключ в закодированном виде
     */
    fun getPrivateKey() = keyEncoder.encodeKey(Pair(d, n))

    /**
     * Поиск открытой экспоненты для публичного ключа.
     *
     * Этот метод находит открытую экспоненту на основе функции Эйлера.
     * Начинаем с 65537 (0b10000000000000001), так как это число рекомендует Public Key Cryptography Standard #1.
     *
     * @param phi значение функции Эйлера
     * @return Открытая экспонента
     */
    private fun findPublicExponent(phi: BigInteger): BigInteger {
        var e = START_E
        while (e.gcd(phi) != BigInteger.ONE) {
            e += BigInteger.TWO
        }
        return e
    }

    /**
     * Вычисление секретной экспоненты для закрытого ключа.
     *
     * Этот метод вычисляет секретную экспоненту на основе открытой экспоненты и функции Эйлера.
     *
     * @param e открытая экспонента
     * @param phi значение функции Эйлера
     * @return Секретная экспонента
     */
    private fun calculatePrivateD(e: BigInteger, phi: BigInteger) =
        MathUtils.calculateModularMultiplicativeInverse(e, phi)

}