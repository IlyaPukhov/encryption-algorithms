package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.DiffieHellmanProtocolService
import com.puhovin.encryption.util.MathUtils
import jakarta.annotation.PostConstruct
import java.math.BigInteger
import java.util.Random
import kotlin.properties.Delegates
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Реализация протокола Диффи-Хеллмана для выработки общего секретного ключа.
 *
 * Сервис предоставляет методы для генерации простых чисел, закрытых и открытых ключей,
 * а также для вычисления общего секретного ключа.
 */
@Service
class DiffieHellmanProtocolServiceImpl : DiffieHellmanProtocolService {

    private val logger = LoggerFactory.getLogger(DiffieHellmanProtocolServiceImpl::class.java)

    private companion object {
        const val BIT_LENGTH = 2048
    }

    // Простые числа w и n, используемые в протоколе Диффи-Хеллмана
    override var w by Delegates.notNull<BigInteger>()
    override var n by Delegates.notNull<BigInteger>()

    /**
     * Инициализация сервиса.
     * Генерирует простые числа w и n.
     */
    @PostConstruct
    fun init() {
        w = generatePrime()
        n = generatePrime()

        logger.info("Сгенерированы параметры w=$w и n=$n")
    }

    /**
     * Генерация закрытого ключа.
     *
     * @return Закрытый ключ.
     */
    override fun generatePrivateKey(): BigInteger {
        return generatePrime()
    }

    /**
     * Генерация открытого ключа на основе закрытого ключа.
     *
     * @param privateKey Закрытый ключ.
     * @return Открытый ключ.
     */
    override fun generatePublicKey(privateKey: BigInteger): BigInteger {
        return MathUtils.modularExponentiation(w, privateKey, n)
    }

    /**
     * Вычисление общего секретного ключа на основе открытого ключа другой стороны и собственного закрытого ключа.
     *
     * @param anotherPublicKey Открытый ключ другой стороны.
     * @param privateKey Собственный закрытый ключ.
     * @return Общий секретный ключ.
     */
    override fun generateSharedSecret(anotherPublicKey: BigInteger, privateKey: BigInteger): BigInteger {
        return MathUtils.modularExponentiation(anotherPublicKey, privateKey, n)
    }

    /**
     * Генерирует простое число длиной [BIT_LENGTH] бит.
     *
     * Этот метод генерирует случайное простое число, используя алгоритм проверки простоты.
     *
     * @return Сгенерированное простое число.
     */
    private fun generatePrime(): BigInteger {
        return BigInteger.probablePrime(BIT_LENGTH, Random())
    }

}