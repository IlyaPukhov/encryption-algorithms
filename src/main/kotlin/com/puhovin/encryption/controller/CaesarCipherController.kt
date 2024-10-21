package com.puhovin.encryption.controller

import com.puhovin.encryption.dto.CaesarCipherRequest
import com.puhovin.encryption.service.CaesarCipherService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/caesar_cipher")
class CaesarCipherController(private val caesarCipherService: CaesarCipherService) {

    @PostMapping("/encode")
    fun caesarCipherEncrypt(@Valid request: CaesarCipherRequest): ResponseEntity<String> {
        return ResponseEntity.ok(caesarCipherService.encrypt(request.message, request.key))
    }

    @PostMapping("/decode")
    fun caesarCipherDecrypt(@Valid request: CaesarCipherRequest): ResponseEntity<String> {
        return ResponseEntity.ok(caesarCipherService.decrypt(request.message, request.key))
    }

}