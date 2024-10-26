package com.puhovin.encryption.service

import com.puhovin.encryption.dto.BruteforceResult
import com.puhovin.encryption.service.impl.CaesarCipherService
import java.io.File
import kotlin.system.measureTimeMillis
import kotlin.text.Charsets.UTF_8
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BruteforceService(
    private val caesarCipherService: CaesarCipherService
) {

    private val logger = LoggerFactory.getLogger(BruteforceService::class.java)

    fun bruteforce(): BruteforceResult {
        val defaultMessage = FileUtils.readFileToString(File("src/main/resources/static/example.txt"), UTF_8)
        return bruteforce(defaultMessage)
    }

    fun bruteforce(message: String): BruteforceResult {
        val randomKey = (1..32).random()

        val encryptedMessage = caesarCipherService.encrypt(message, randomKey.toString())
        val bruteforceTime = measureBruteforceTime(encryptedMessage, message)

        return BruteforceResult(message, randomKey, bruteforceTime, encryptedMessage)
    }

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