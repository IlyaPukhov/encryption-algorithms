package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.MessageService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.system.measureTimeMillis

@Service
class TestBruteforceService(
    private val caesarCipherService: CaesarCipherService,
    private val messageService: MessageService
) {

    private val logger = LoggerFactory.getLogger(TestBruteforceService::class.java)

    fun testBruteforce(): String {
        val randomMessage = messageService.getMessage("test.bruteforce.message")
        val randomKey = (1..32).random()

        val encryptedMessage = caesarCipherService.encrypt(randomMessage, randomKey)
        val bruteforceTime = measureBruteforceTime(encryptedMessage, randomMessage)

        return "Сообщение: \"$randomMessage\" длиной ${randomMessage.length} символов, зашифрованное ключом '$randomKey', " +
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