package com.puhovin.encryption.controller

import com.puhovin.encryption.dto.CaesarCipherRequest
import com.puhovin.encryption.service.CaesarCipherService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/caesar_cipher")
class CaesarCipherController(private val caesarCipherService: CaesarCipherService) {

    private val logger = LoggerFactory.getLogger(CaesarCipherController::class.java)

    @PostMapping("/{action}")
    fun caesarCipher(
        @PathVariable action: String,
        @Valid request: CaesarCipherRequest
    ): ResponseEntity<String> {
        logger.info("Received request to $action with message: ${request.message} and key: ${request.key}")
        return when (action.lowercase()) {
            "encode" -> ResponseEntity.ok(caesarCipherService.encrypt(request.message, request.key))
            "decode" -> ResponseEntity.ok(caesarCipherService.decrypt(request.message, request.key))
            else -> ResponseEntity.badRequest().body("Invalid action: $action")
        }
    }
}
