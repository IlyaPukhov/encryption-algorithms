package com.puhovin.encryption.controller

import com.puhovin.encryption.dto.BruteforceResult
import com.puhovin.encryption.dto.CaesarCipherRequest
import com.puhovin.encryption.service.BruteforceCaesarCipherService
import com.puhovin.encryption.service.CipherService
import com.puhovin.encryption.validation.CipherActionValidator
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * REST-контроллер для шифрования и дешифрования методом Цезаря.
 */
@RestController
@RequestMapping("\${server.prefix}/caesar_cipher")
class CaesarCipherController(
    private val caesarCipherService: CipherService,
    private val bruteforceService: BruteforceCaesarCipherService
) {

    private val logger = LoggerFactory.getLogger(CaesarCipherController::class.java)

    /**
     * Обрабатывает POST-запросы на шифрование и дешифрование методом Цезаря.
     *
     * @param action тип операции (encrypt или decrypt)
     * @param request запрос, содержащий сообщение и ключ
     * @return Результат операции в виде строки
     */
    @PostMapping("/{action}")
    fun cipher(
        @PathVariable action: String,
        @Valid @RequestBody request: CaesarCipherRequest
    ): ResponseEntity<String> {
        logger.info("Получен запрос на $action с сообщением: \"${request.message}\" и ключом: ${request.key}")

        val result = when (action.lowercase()) {
            "encrypt" -> caesarCipherService.encrypt(request.message, request.key.toString())
            "decrypt" -> caesarCipherService.decrypt(request.message, request.key.toString())
            else -> throw CipherActionValidator.getValidationException(this, action)
        }

        return ResponseEntity.ok("{\"message\": \"$result\"}")
    }

    /**
     * Обрабатывает GET-запросы на дешифрование перебором.
     *
     * @param isDefault использовать ли сообщение по умолчанию (необязательно, по умолчанию true)
     * @param request запрос, содержащий сообщение и ключ (необязательно)
     * @return [ResponseEntity], содержащий результат дешифрования [BruteforceResult]
     */
    @PostMapping("/hack/bruteforce")
    fun bruteforce(
        @RequestParam(required = false, defaultValue = "true") isDefault: Boolean,
        @Valid @RequestBody(required = false) request: CaesarCipherRequest?
    ): ResponseEntity<BruteforceResult> {
        val result = if (isDefault) {
            bruteforceService.bruteforce()
        } else {
            bruteforceService.bruteforce(request!!.message, request.key)
        }

        return ResponseEntity.ok(result)
    }

}