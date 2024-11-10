package com.puhovin.encryption.service

interface DiffieHellmanProtocolService {

    var w: Long
    var n: Long

    fun generatePrivateKey(): Long

    fun generatePublicKey(privateKey: Long): Long

    fun generateSharedSecret(anotherPublicKey: Long, privateKey: Long): Long
}