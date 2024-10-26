package com.puhovin.encryption.service

import com.puhovin.encryption.util.KeyEncoderDecoder
import com.puhovin.encryption.util.MathUtils
import com.puhovin.encryption.util.MessageService
import jakarta.annotation.PostConstruct
import kotlin.properties.Delegates
import kotlin.system.measureTimeMillis
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * Сервис, ответственный за генерацию ключей RSA.
 *
 * Этот сервис генерирует ключи RSA на основе заданных простых чисел p и q.
 */
@Service
class RsaKeysGeneratorService(
    private val keyEncoder: KeyEncoderDecoder,
    private val messageService: MessageService,
) {

    private val logger = LoggerFactory.getLogger(RsaKeysGeneratorService::class.java)

    @Value("\${encrypt.rsa.p}")
    private var p: Long = 0

    @Value("\${encrypt.rsa.q}")
    private var q: Long = 0

    private var n by Delegates.notNull<Long>()
    private var e by Delegates.notNull<Long>()
    private var d by Delegates.notNull<Long>()

    /**
     * Инициализация сервиса.
     *
     * Этот метод вызывается после запуска приложения.
     * Он проверяет, что простые числа p и q заданы, и генерирует ключи RSA единоразово на все время работы приложения.
     */
    @PostConstruct
    fun init() {
        if (p == 0L || q == 0L) {
            throw IllegalStateException(messageService.getMessage("error.rsa-encrypt.pq-is-not-initialized"))
        }

        val duration = measureTimeMillis {
            n = p * q
            val phi = (p - 1) * (q - 1)
            e = findPublicExponent(phi)
            d = calculatePrivateD(e, phi)
        }
        logger.info("Время вычисления ключа: $duration мс")
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
     *
     * @param phi значение функции Эйлера
     * @return Открытая экспонента
     */
    private fun findPublicExponent(phi: Long): Long {
        var e = 3L
        while (MathUtils.gcd(e, phi) != 1L) {
            e += 2
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
    private fun calculatePrivateD(e: Long, phi: Long) = MathUtils.modularInverse(e, phi)

}