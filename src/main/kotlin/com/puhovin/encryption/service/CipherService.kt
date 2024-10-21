package com.puhovin.encryption.service

interface CipherService {

    fun encrypt(rawMessage: String, key: Int? = null): String

    fun decrypt(encryptedMessage: String, key: Int? = null): String

}