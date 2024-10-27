package com.puhovin.encryption.dto

/**
 * Дата-класс, представляющий результат операции дешифрования перебором.
 *
 * @property rawMessage исходное сообщение, которое было зашифровано
 * @property key ключ, использованный для шифрования сообщения
 * @property bruteforceTime время, затраченное на перебор
 */
data class BruteforceResult(
    val rawMessage: String,
    val key: Int,
    val bruteforceTime: Long
)