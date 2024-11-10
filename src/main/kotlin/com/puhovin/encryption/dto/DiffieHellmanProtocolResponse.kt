package com.puhovin.encryption.dto

import java.math.BigInteger

/**
 * Класс, представляющий ответ на запрос по протоколу Диффи-Хеллмана.
 *
 * @property w Первое простое число, используемое в протоколе.
 * @property n Второе простое число, используемое в протоколе.
 * @property xA Закрытый ключ для стороны A.
 * @property xB Закрытый ключ для стороны B.
 * @property yA Открытый ключ для стороны A.
 * @property yB Открытый ключ для стороны B.
 * @property kAB Общий секретный ключ, вычисленный сторонами A и B.
 */
data class DiffieHellmanProtocolResponse(
    val w: BigInteger,
    val n: BigInteger,
    val xA: BigInteger,
    val xB: BigInteger,
    val yA: BigInteger,
    val yB: BigInteger,
    val kAB: BigInteger
)