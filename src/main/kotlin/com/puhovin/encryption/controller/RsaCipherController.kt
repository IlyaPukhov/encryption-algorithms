package com.puhovin.encryption.controller

import com.puhovin.encryption.service.CipherService
import jakarta.validation.constraints.NotNull
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rsa_cipher")
class RsaCipherController(private val rsaCipherService: CipherService) {

    private val logger = LoggerFactory.getLogger(RsaCipherController::class.java)

    @PostMapping("/{action}")
    fun rsaCipher(
        @PathVariable action: String,
        @NotNull message: String
    ): ResponseEntity<String> {
        logger.info("Получен запрос на $action с сообщением: $message")

        return when (action.lowercase()) {
            "encode" -> ResponseEntity.ok(rsaCipherService.encrypt(message))
            "decode" -> ResponseEntity.ok(rsaCipherService.decrypt(message))
            else -> ResponseEntity.badRequest()
                .body("Некорректное действие: $action. Допустимые значения: encode, decode")
        }
    }

}