package com.puhovin.encryption.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

/**
 * Запрос на шифрование или расшифрование методом Цезаря.
 *
 * @property message сообщение для шифрования или расшифрования
 * @property key ключ шифрования (сдвиг алфавита)
 */
data class CaesarCipherRequest(

    /**
     * Сообщение для шифрования или расшифрования.
     * Не может быть null.
     */
    @field:NotBlank(message = "{error.bad-request.message.is.required}")
    val message: String,

    /**
     * Ключ шифрования (сдвиг алфавита).
     * Должен быть в диапазоне от 1 до 32.
     * Не может быть null.
     */
    @field:NotNull(message = "{error.bad-request.key.is.required}")
    @field:Range(min = 1, max = 32, message = "{error.bad-request.key.range.is.invalid}")
    val key: Int
)