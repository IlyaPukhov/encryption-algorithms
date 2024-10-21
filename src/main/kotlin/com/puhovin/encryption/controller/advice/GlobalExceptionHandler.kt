package com.puhovin.encryption.controller.advice

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler()