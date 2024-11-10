package com.puhovin.encryption.config

import java.math.BigInteger
import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Класс конфигурации свойств RSA.
 *
 * @property p первое простое число
 * @property q второе простое число
 */
@ConfigurationProperties(prefix = "encrypt.rsa")
data class RsaProperties(
    var p: BigInteger?,
    var q: BigInteger?
)