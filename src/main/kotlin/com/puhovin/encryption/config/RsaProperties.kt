package com.puhovin.encryption.config

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Класс конфигурации свойств RSA.
 *
 * @property p первое простое число
 * @property q второе простое число
 */
@ConfigurationProperties(prefix = "encrypt.rsa")
data class RsaProperties(
    var p: Int?,
    var q: Int?
)