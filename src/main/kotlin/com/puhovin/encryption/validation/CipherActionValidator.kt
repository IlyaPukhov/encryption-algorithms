package com.puhovin.encryption.validation

import com.puhovin.encryption.dto.CaesarCipherRequest
import java.lang.reflect.Method
import org.springframework.core.MethodParameter
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.web.bind.MethodArgumentNotValidException

object CipherActionValidator {

    fun getValidationException(
        bean: Any,
        action: String
    ): Exception {
        val bindingResult = BeanPropertyBindingResult(bean, "request")
        val errorMessage = "Некорректное действие: ${action}. Допустимые значения: encrypt, decrypt"
        bindingResult.reject("invalid.action", errorMessage)
        val method: Method = bean.javaClass.getMethod("cipher", String::class.java, CaesarCipherRequest::class.java)
        val methodParameter = MethodParameter(method, 0)
        throw MethodArgumentNotValidException(methodParameter, bindingResult)
    }
}