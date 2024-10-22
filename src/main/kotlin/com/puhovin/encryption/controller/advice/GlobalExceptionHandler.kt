package com.puhovin.encryption.controller.advice

import com.puhovin.encryption.service.MessageService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(private val messageService: MessageService) : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        logger.error("Произошла ошибка валидации: ${ex.message}", ex)

        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            messageService.getMessage("errors.400.detail")
        )
        problemDetail.setProperty(
            "errors",
            ex.allErrors.stream()
                .map(ObjectError::getDefaultMessage)
                .toList()
        )

        return ResponseEntity.of(problemDetail).build()
    }

}