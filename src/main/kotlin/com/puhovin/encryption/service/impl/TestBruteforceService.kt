package com.puhovin.encryption.service.impl

import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import kotlin.system.measureTimeMillis
import kotlin.text.Charsets.UTF_8

@Service
class TestBruteforceService(private val caesarCipherService: CaesarCipherService) {

    private val logger = LoggerFactory.getLogger(TestBruteforceService::class.java)

    fun testBruteforce(): String {
        val randomMessage = FileUtils.readFileToString(File("src/main/resources/static/example.txt"), UTF_8)
        val randomKey = (1..32).random()

        val encryptedMessage = caesarCipherService.encrypt(randomMessage, randomKey)
        val bruteforceTime = measureBruteforceTime(encryptedMessage, randomMessage)

        return "Сообщение длиной ${randomMessage.length} символов, зашифрованное ключом '$randomKey', " +
                "было расшифровано за $bruteforceTime мс"
    }

    fun measureBruteforceTime(encryptedMessage: String, targetMessage: String): Long {
        return measureTimeMillis {
            for (key in 1..32) {
                val decryptedMessage = caesarCipherService.decrypt(encryptedMessage, key)
                logger.info("Ключ: $key, Сообщение: $decryptedMessage")
                if (decryptedMessage == targetMessage) break
            }
        }
    }

}