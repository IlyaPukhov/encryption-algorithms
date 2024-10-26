package com.puhovin.encryption.service

/**
 * Интерфейс сервиса по шифрованию и дешифрованию.
 *
 * Этот интерфейс определяет два метода: шифрование и дешифрование сообщений.
 */
interface CipherService {

    /**
     * Шифрует исходное сообщение.
     *
     * @param rawMessage исходное сообщение
     * @param key ключ шифрования
     * @return Зашифрованное сообщение
     */
    fun encrypt(rawMessage: String, key: String): String

    /**
     * Расшифровывает зашифрованное сообщение.
     *
     * @param encryptedMessage зашифрованное сообщение
     * @param key ключ дешифрования
     * @return Расшифрованное сообщение
     */
    fun decrypt(encryptedMessage: String, key: String): String

}