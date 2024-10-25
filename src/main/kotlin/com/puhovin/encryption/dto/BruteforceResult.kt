package com.puhovin.encryption.dto

data class BruteforceResult(
    val rawMessage: String,
    val key: Int,
    val bruteforceTime: Long,
    val encryptedMessage: String
)