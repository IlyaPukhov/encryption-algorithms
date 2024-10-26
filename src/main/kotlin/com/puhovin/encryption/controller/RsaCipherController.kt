package com.puhovin.encryption.controller

import com.puhovin.encryption.dto.RsaCipherRequest
import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.service.RsaKeysGeneratorService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST-контроллер для шифрования и дешифрования методом RSA.
 */
@RestController
@RequestMapping("/rsa_cipher")
class RsaCipherController(
    private val rsaCipherService: CipherService,
    private val keyGeneratorService: RsaKeysGeneratorService
) {

    private val logger = LoggerFactory.getLogger(RsaCipherController::class.java)

    /**
     * Возвращает открытый ключ RSA.
     *
     * @return Открытый ключ RSA в виде закодированной строки
     */
    @GetMapping("/open_key")
    fun publicKey(): ResponseEntity<String> {
        return ResponseEntity.ok(keyGeneratorService.getPublicKey())
    }

    /**
     * Возвращает закрытый ключ RSA.
     *
     * @return Закрытый ключ RSA в виде закодированной строки
     */
    @GetMapping("/close_key")
    fun privateKey(): ResponseEntity<String> {
        return ResponseEntity.ok(keyGeneratorService.getPrivateKey())
    }

    /**
     * Выполняет операцию шифрования или дешифрования методом RSA.
     *
     * @param action тип операции (encode или decode)
     * @param request запрос, содержащий сообщение и ключ
     * @return Результат операции в виде строки
     */
    @PostMapping("/{action}")
    fun rsaCipher(
        @PathVariable action: String,
        @Valid request: RsaCipherRequest
    ): ResponseEntity<String> {
        logger.info("Получен запрос на $action с сообщением: ${request.message} и ключом: ${request.key}")

        return when (action.lowercase()) {
            "encode" -> ResponseEntity.ok(rsaCipherService.encrypt(request.message!!, request.key!!))
            "decode" -> ResponseEntity.ok(rsaCipherService.decrypt(request.message!!, request.key!!))
            else -> ResponseEntity.badRequest()
                .body("Некорректное действие: $action. Допустимые значения: encode, decode")
        }
    }

}