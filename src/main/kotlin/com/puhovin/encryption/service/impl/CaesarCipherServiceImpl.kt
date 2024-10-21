package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.CaesarCipherService
import org.springframework.stereotype.Service

@Service
class CaesarCipherServiceImpl : CaesarCipherService {

    override fun encrypt(rawMessage: String, key: Int): String {
        TODO("Not yet implemented")
    }

    override fun decrypt(encryptedMessage: String, key: Int): String {
        TODO("Not yet implemented")
    }

}
