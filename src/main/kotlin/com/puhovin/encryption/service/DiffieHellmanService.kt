package com.puhovin.encryption.service

interface DiffieHellmanService {

    fun generatePrivateKey(): Long

    fun generatePublicKey(privateKey: Long): Long

    fun generateSharedSecret(anotherPublicKey: Long, privateKey: Long): Long
}