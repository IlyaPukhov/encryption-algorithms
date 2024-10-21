package com.puhovin.encryption.service

interface CipherService {

    fun encrypt(rawMessage: String, key: Int): String

    fun decrypt(encryptedMessage: String, key: Int): String
}
