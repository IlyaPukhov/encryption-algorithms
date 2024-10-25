package com.puhovin.encryption.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "encrypt.rsa")
data class RsaProperties(
    var p: Int?,
    var q: Int?
)
