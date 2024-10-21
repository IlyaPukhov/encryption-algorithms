package com.puhovin.encryption

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EncryptionAlgorithmsApplication

fun main(args: Array<String>) {
    runApplication<EncryptionAlgorithmsApplication>(*args)
}