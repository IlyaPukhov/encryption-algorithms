package com.puhovin.encryption.controller

import com.puhovin.encryption.dto.DiffieHellmanProtocolResponse
import com.puhovin.encryption.service.DiffieHellmanProtocolService
import com.puhovin.encryption.util.MessageService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.reactor.mono
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 * Контроллер для протокола Диффи-Хеллмана.
 *
 * Этот контроллер обрабатывает запросы на получение общего секретного ключа
 * с использованием протокола Диффи-Хеллмана.
 */
@RestController
@RequestMapping("/diffie_hellman")
class DiffieHellmanProtocolController(
    private val protocolService: DiffieHellmanProtocolService,
    private val messageService: MessageService
) {

    /**
     * Получение общего секретного ключа.
     *
     * Этот метод генерирует закрытые ключи для обеих сторон,
     * вычисляет открытые ключи и вычисляет общий секретный ключ.
     * Если оба ключа не совпадают, то выбрасывается исключение.
     *
     * @return Ответ с объектом [DiffieHellmanProtocolResponse], содержащим
     *         простые числа w и n, закрытые ключи xA и xB, открытые ключи yA и yB,
     *         а также общий секретный ключ sharedSecretA.
     */
    @GetMapping
    fun getSharedSecret(): Mono<ResponseEntity<DiffieHellmanProtocolResponse>> = mono {
        val (xA, xB) = awaitAll(
            async { protocolService.generatePrivateKey() },
            async { protocolService.generatePrivateKey() }
        )

        val (yA, yB) = awaitAll(
            async { protocolService.generatePublicKey(xA) },
            async { protocolService.generatePublicKey(xB) }
        )

        val (sharedSecretA, sharedSecretB) = awaitAll(
            async { protocolService.generateSharedSecret(yB, xA) },
            async { protocolService.generateSharedSecret(yA, xB) }
        )

        if (sharedSecretA != sharedSecretB) {
            throw IllegalArgumentException(messageService.getMessage("error.diffie-hellman-protocol.shared-secrets-is-not-equal"))
        }

        return@mono ResponseEntity.ok(
            DiffieHellmanProtocolResponse(
                protocolService.w,
                protocolService.n,
                xA,
                yA,
                xB,
                yB,
                sharedSecretA
            )
        )
    }

}