package com.puhovin.encryption.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class RsaCipherRequest(

    @field:NotNull(message = "{error.bad-request.message.is.required}")
    val message: String?,

    @field:NotBlank(message = "{error.bad-request.key.is.required}")
    val key: String?
)