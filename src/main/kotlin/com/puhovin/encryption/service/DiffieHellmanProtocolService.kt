package com.puhovin.encryption.service

import java.math.BigInteger

interface DiffieHellmanProtocolService {

    var w: BigInteger
    var n: BigInteger

    fun generatePrivateKey(): BigInteger

    fun generatePublicKey(privateKey: BigInteger): BigInteger

    fun generateSharedSecret(anotherPublicKey: BigInteger, privateKey: BigInteger): BigInteger
}