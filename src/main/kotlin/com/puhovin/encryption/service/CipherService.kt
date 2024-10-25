package com.puhovin.encryption.service

interface CipherService {

    fun encrypt(rawMessage: String, key: String? = null): String

    fun decrypt(encryptedMessage: String, key: String? = null): String

}