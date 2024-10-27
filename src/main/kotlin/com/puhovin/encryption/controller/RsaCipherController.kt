package com.puhovin.encryption.controller

import com.puhovin.encryption.dto.RsaCipherRequest
import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.service.RsaKeysGenerationService
import com.puhovin.encryption.validation.CipherActionValidator
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST-контроллер для шифрования и дешифрования методом RSA.
 */
@RestController
@RequestMapping("/rsa_cipher")
class RsaCipherController(
    private val rsaCipherService: CipherService,
    private val keysGenerationService: RsaKeysGenerationService
) {

    private val logger = LoggerFactory.getLogger(RsaCipherController::class.java)

    /**
     * Возвращает открытый ключ RSA.
     *
     * @return Открытый ключ RSA в виде закодированной строки
     */
    @GetMapping("/public_key")
    fun publicKey(): ResponseEntity<String> {
        return ResponseEntity.ok("{\"key\": \"${keysGenerationService.getPublicKey()}\"}")
    }

    /**
     * Возвращает закрытый ключ RSA.
     *
     * @return Закрытый ключ RSA в виде закодированной строки
     */
    @GetMapping("/private_key")
    fun privateKey(): ResponseEntity<String> {
        return ResponseEntity.ok("{\"key\": \"${keysGenerationService.getPrivateKey()}\"}")

    }

    /**
     * Выполняет операцию шифрования или дешифрования методом RSA.
     *
     * @param action тип операции (encrypt или decrypt)
     * @param request запрос, содержащий сообщение и ключ
     * @return Результат операции в виде строки
     */
    @PostMapping("/{action}")
    fun cipher(
        @PathVariable action: String,
        @Valid @RequestBody request: RsaCipherRequest
    ): ResponseEntity<String> {
        logger.info("Получен запрос на $action с сообщением: \"${request.message}\" и ключом: ${request.key}")

        val result = when (action.lowercase()) {
            "encrypt" -> rsaCipherService.encrypt(request.message, request.key)
            "decrypt" -> rsaCipherService.decrypt(request.message, request.key)
            else -> throw CipherActionValidator.getValidationException(this, action)
        }

        return ResponseEntity.ok("{\"message\": \"$result\"}")
    }

}