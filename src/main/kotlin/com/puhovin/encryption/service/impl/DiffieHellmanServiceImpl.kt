package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.DiffieHellmanService
import com.puhovin.encryption.util.MathUtils
import jakarta.annotation.PostConstruct
import kotlin.properties.Delegates
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/**
 * Реализация протокола Диффи-Хеллмана для выработки общего секретного ключа.
 *
 * Сервис предоставляет методы для генерации простых чисел, закрытых и открытых ключей,
 * а также для вычисления общего секретного ключа.
 */
@Service
class DiffieHellmanServiceImpl : DiffieHellmanService {

    private val logger = LoggerFactory.getLogger(DiffieHellmanServiceImpl::class.java)

    // Минимальное значение диапазона для генерации простых чисел
    @Value("\${encrypt.diffie-hellman.min}")
    private val minNumber = 0L

    // Максимальное значение диапазона для генерации простых чисел
    @Value("\${encrypt.diffie-hellman.max}")
    private val maxNumber = 0L

    // Простые числа w и n, используемые в протоколе Диффи-Хеллмана
    private var w by Delegates.notNull<Long>()
    private var n by Delegates.notNull<Long>()

    /**
     * Инициализация сервиса.
     * Генерирует простые числа w и n.
     */
    @PostConstruct
    fun init() {
        w = generatePrime()
        n = generatePrime()
    }

    /**
     * Генерация закрытого ключа.
     *
     * @return Закрытый ключ.
     */
    override fun generatePrivateKey(): Long {
        return generatePrime()
    }

    /**
     * Генерация открытого ключа на основе закрытого ключа.
     *
     * @param privateKey Закрытый ключ.
     * @return Открытый ключ.
     */
    override fun generatePublicKey(privateKey: Long): Long {
        return MathUtils.modularExponentiation(w, privateKey, n)
    }

    /**
     * Вычисление общего секретного ключа на основе открытого ключа другой стороны и собственного закрытого ключа.
     *
     * @param anotherPublicKey Открытый ключ другой стороны.
     * @param privateKey Собственный закрытый ключ.
     * @return Общий секретный ключ.
     */
    override fun generateSharedSecret(anotherPublicKey: Long, privateKey: Long): Long {
        return MathUtils.modularExponentiation(anotherPublicKey, privateKey, n)
    }

    /**
     * Генерирует простое число в диапазоне от [minNumber] до [maxNumber].
     *
     * Этот метод генерирует случайное простое число, используя алгоритм проверки простоты.
     *
     * @return Сгенерированное простое число.
     */
    private fun generatePrime(): Long {
        var candidate = (minNumber..maxNumber).random() or 1L

        while (!MathUtils.isPrime(candidate)) {
            candidate += 2
            if (candidate % 3 == 0L) candidate += 2
        }
        logger.info("Сгенерировано простое число X=$candidate")
        return candidate
    }

}