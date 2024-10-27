package com.puhovin.encryption.dto

import jakarta.validation.constraints.NotBlank

/**
 * Запрос на шифрование или расшифрование по алгоритму RSA.
 *
 * @property message сообщение для шифрования или расшифрования
 * @property key ключ шифрования.
 */
data class RsaCipherRequest(
    /**
     * Сообщение для шифрования или расшифрования.
     * Не может быть null.
     */
    @field:NotBlank(message = "{error.bad-request.message.is.required}")
    val message: String,

    /**
     * Ключ для шифрования.
     * Не может быть пустым или отсутствовать.
     */
    @field:NotBlank(message = "{error.bad-request.key.is.required}")
    val key: String
)