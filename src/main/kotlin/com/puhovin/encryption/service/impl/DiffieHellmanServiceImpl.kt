package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.DiffieHellmanService
import com.puhovin.encryption.util.MathUtils
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

    @Value("\${encrypt.diffie-hellman.min}")
    private val minNumber = 0L

    @Value("\${encrypt.diffie-hellman.max}")
    private val maxNumber = 0L

    /**
     * Генерирует простое число в диапазоне от [minNumber] до [maxNumber].
     *
     * Этот метод генерирует случайное простое число, используя алгоритм проверки простоты.
     *
     * @return Сгенерированное простое число.
     */
    override fun generatePrime(): Long {
        var candidate = (minNumber..maxNumber).random() or 1L

        while (!MathUtils.isPrime(candidate)) {
            candidate += 2
            if (candidate % 3 == 0L) candidate += 2
        }
        logger.info("Сгенерировано простое число X=$candidate")
        return candidate
    }

    override fun generatePrivateKey(): Long {
        TODO("Not yet implemented")
    }

    override fun generatePublicKey(privateKey: Long): Long {
        TODO("Not yet implemented")
    }

    override fun generateSharedSecret(privateKey: Long, publicKey: Long): Long {
        TODO("Not yet implemented")
    }
}