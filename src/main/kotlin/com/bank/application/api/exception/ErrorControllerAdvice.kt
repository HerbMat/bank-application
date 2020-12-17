package com.bank.application.api.exception

import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorControllerAdvice {

    @ExceptionHandler( value = [BindException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBeanValidationException(ex: BindException): List<String> {
        return ex.allErrors.map { it.defaultMessage!! }
    }
}