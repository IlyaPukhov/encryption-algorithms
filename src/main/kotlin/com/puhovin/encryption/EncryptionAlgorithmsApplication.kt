package com.puhovin.encryption

import com.puhovin.encryption.config.RsaProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(RsaProperties::class)
class EncryptionAlgorithmsApplication

fun main(args: Array<String>) {
    runApplication<EncryptionAlgorithmsApplication>(*args)
}