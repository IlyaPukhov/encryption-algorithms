package com.puhovin.encryption.service.impl

import com.puhovin.encryption.service.DiffieHellmanService
import com.puhovin.encryption.util.MathUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class DiffieHellmanServiceImpl : DiffieHellmanService {

    private val logger = LoggerFactory.getLogger(DiffieHellmanServiceImpl::class.java)

    override fun generatePrime(): Long {
        var candidate = (1L..Long.MAX_VALUE).random() or 1L

        while (!MathUtils.isPrime(candidate)) {
            candidate += 2
            if (candidate % 3 == 0L) candidate += 2
        }
        logger.info("Сгенерировано простое число X=$candidate")
        return candidate
    }

    override fun generatePrivateKey(): Long {
        TODO("Not yet implemented")
    }

    override fun generatePublicKey(privateKey: Long): Long {
        TODO("Not yet implemented")
    }

    override fun generateSharedSecret(privateKey: Long, publicKey: Long): Long {
        TODO("Not yet implemented")
    }
}