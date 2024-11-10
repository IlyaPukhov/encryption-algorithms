package com.puhovin.encryption.dto

/**
 * Класс, представляющий ответ на запрос по протоколу Диффи-Хеллмана.
 *
 * @property w Первое простое число, используемое в протоколе.
 * @property n Второе простое число, используемое в протоколе.
 * @property xA Закрытый ключ для стороны A.
 * @property yA Открытый ключ для стороны A.
 * @property xB Закрытый ключ для стороны B.
 * @property yB Открытый ключ для стороны B.
 * @property kAB Общий секретный ключ, вычисленный сторонами A и B.
 */
data class DiffieHellmanProtocolResponse(
    val w: Long,
    val n: Long,
    val xA: Long,
    val yA: Long,
    val xB: Long,
    val yB: Long,
    val kAB: Long
)