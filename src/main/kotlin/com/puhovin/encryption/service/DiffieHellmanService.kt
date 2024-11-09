package com.puhovin.encryption.service

interface DiffieHellmanService {

    fun generatePrime(): Long

    fun generatePrivateKey(): Long

    fun generatePublicKey(privateKey: Long): Long

    fun generateSharedSecret(privateKey: Long, publicKey: Long): Long
}