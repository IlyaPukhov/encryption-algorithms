package com.puhovin.encryption.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

data class CaesarCipherRequest(

    @field:NotNull(message = "{error.bad-request.message.is.required}")
    val message: String?,

    @field:NotNull(message = "{error.bad-request.key.is.required}")
    @field:Range(min = 1, max = 32, message = "{error.bad-request.key.range.is.invalid}")
    val key: Int?
)