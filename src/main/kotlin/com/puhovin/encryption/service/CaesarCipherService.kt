package com.puhovin.encryption.service

interface CaesarCipherService {

    fun encrypt(rawMessage: String, key: Int): String

    fun decrypt(encryptedMessage: String, key: Int): String
}