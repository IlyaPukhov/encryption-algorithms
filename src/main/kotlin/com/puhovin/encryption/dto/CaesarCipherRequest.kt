package com.puhovin.encryption.dto

import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Range

data class CaesarCipherRequest(
    @get:NotNull val message: String,
    @get:NotNull @get:Range(min = 1, max = 32) val key: Int = 3
)