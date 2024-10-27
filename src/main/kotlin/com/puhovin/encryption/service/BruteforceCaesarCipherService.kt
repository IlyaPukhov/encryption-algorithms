package com.puhovin.encryption.service

import com.puhovin.encryption.dto.BruteforceResult
import com.puhovin.encryption.service.impl.CaesarCipherService
import java.io.File
import kotlin.system.measureTimeMillis
import kotlin.text.Charsets.UTF_8
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * Сервис, реализующий метод дешифрования сообщения, зашифрованного методом, Цезаря с помощью перебора ключей.
 */
@Service
class BruteforceCaesarCipherService(private val caesarCipherService: CaesarCipherService) {

    private val logger = LoggerFactory.getLogger(BruteforceCaesarCipherService::class.java)

    /**
     * Использует сообщение по умолчанию из файла example.txt.
     *
     * @return Результат дешифрования [BruteforceResult]
     */
    fun bruteforce(): BruteforceResult {
        val defaultMessage = FileUtils.readFileToString(File("src/main/resources/static/example.txt"), UTF_8)
        val randomKey = (1..32).random()
        return bruteforce(defaultMessage, randomKey)
    }

    /**
     * Шифрует заданное сообщение методом Цезаря, а затем дешифрует с помощью перебора ключей.
     *
     * @param message сообщение для дешифрования
     * @return Результат дешифрования [BruteforceResult]
     */
    fun bruteforce(message: String, key: Int): BruteforceResult {
        val encryptedMessage = caesarCipherService.encrypt(message, key.toString())
        val bruteforceTime = measureBruteforceTime(encryptedMessage, message)

        return BruteforceResult(message, key, bruteforceTime)
    }

    /**
     * Измеряет время, затраченное на дешифрование методом перебора ключей.
     *
     * @param encryptedMessage зашифрованное сообщение
     * @param targetMessage исходное сообщение
     * @return Время (в мс), затраченное на дешифрование
     */
    private fun measureBruteforceTime(encryptedMessage: String, targetMessage: String): Long {
        return measureTimeMillis {
            for (key in 1..32) {
                val decryptedMessage = caesarCipherService.decrypt(encryptedMessage, key.toString())
                logger.info("Ключ: $key, Сообщение: $decryptedMessage")
                if (decryptedMessage == targetMessage) {
                    logger.info("Расшифровка завершение!")
                    break
                }
            }
        }
    }

}