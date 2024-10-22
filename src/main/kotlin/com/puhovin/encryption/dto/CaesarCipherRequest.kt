package com.puhovin.encryption.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

data class CaesarCipherRequest(
    @field:NotNull val message: String,
    @field:NotNull @field:Range(min = 1, max = 32) val key: Int = 3
)