package com.puhovin.encryption.controller

import com.puhovin.encryption.dto.BruteforceResult
import com.puhovin.encryption.dto.CaesarCipherRequest
import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.service.impl.BruteforceService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/caesar_cipher")
class CaesarCipherController(
    private val caesarCipherService: CipherService,
    private val bruteforceService: BruteforceService
) {

    private val logger = LoggerFactory.getLogger(CaesarCipherController::class.java)

    @PostMapping("/{action}")
    fun caesarCipher(
        @PathVariable action: String,
        @Valid request: CaesarCipherRequest
    ): ResponseEntity<String> {
        logger.info("Получен запрос на $action с сообщением: ${request.message} и ключом: ${request.key}")

        return when (action.lowercase()) {
            "encode" -> ResponseEntity.ok(caesarCipherService.encrypt(request.message!!, request.key.toString()))
            "decode" -> ResponseEntity.ok(caesarCipherService.decrypt(request.message!!, request.key.toString()))
            else -> ResponseEntity.badRequest()
                .body("Некорректное действие: $action. Допустимые значения: encode, decode")
        }
    }

    @GetMapping("/bruteforce")
    fun bruteforce(
        @RequestParam(required = false) targetMessage: String?,
        @RequestParam(required = false, defaultValue = "true") isDefault: Boolean
    ): ResponseEntity<BruteforceResult> {
        val result = if (isDefault) {
            bruteforceService.bruteforce()
        } else  {
            bruteforceService.bruteforce(targetMessage!!)
        }

        return ResponseEntity.ok(result)
    }

}