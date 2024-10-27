package com.puhovin.encryption.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {

    @Value("\${cors.allowed.origins:http://localhost}")
    private lateinit var allowedOrigins: List<String>

    @Value("\${cors.allowed.methods:GET,POST}")
    private lateinit var allowedMethods: List<String>

    @Value("\${cors.allowed.headers:*}")
    private lateinit var allowedHeaders: List<String>

    @Value("\${cors.mapping:/**}")
    private lateinit var mapping: String

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping(mapping)
            .allowedOrigins(*allowedOrigins.toTypedArray())
            .allowedMethods(*allowedMethods.toTypedArray())
            .allowedHeaders(*allowedHeaders.toTypedArray())
            .allowCredentials(true)
    }

}