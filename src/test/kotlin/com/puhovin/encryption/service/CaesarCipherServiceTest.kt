package com.puhovin.encryption.service

import com.puhovin.encryption.service.impl.CaesarCipherService
import com.puhovin.encryption.service.impl.MessageServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.doReturn
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CaesarCipherServiceTest {

    @Mock
    private lateinit var messageService: MessageServiceImpl

    @InjectMocks
    private lateinit var caesarCipherService: CaesarCipherService

    @BeforeEach
    fun setUp() {
        doReturn("ERROR").`when`(messageService).getMessage(any())
    }

    @Test
    fun encrypt() {
    }

    @Test
    fun decrypt() {
    }

}