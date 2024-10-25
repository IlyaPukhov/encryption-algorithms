package com.puhovin.encryption.service.impl

import jakarta.annotation.PostConstruct
import kotlin.properties.Delegates
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class RsaKeyGeneratorService {

    @get:Value("\${encrypt.rsa.p}")
    private var p by Delegates.notNull<Int>()

    @get:Value("\${encrypt.rsa.q}")
    private var q by Delegates.notNull<Int>()

    private var n by Delegates.notNull<Int>()

    @PostConstruct
    fun init() {
        n = p * q
        val phi = (p - 1) * (q - 1)
    }

    fun generatePublicKey(): String {
        // TODO
        return ""
    }

    fun generatePrivateKey(): String {
        // TODO
        return ""
    }

}