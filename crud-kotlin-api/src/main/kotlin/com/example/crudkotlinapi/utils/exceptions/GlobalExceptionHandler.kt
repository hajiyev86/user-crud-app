package com.example.crudkotlinapi.utils.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundExceptionHandler(ex: UserNotFoundException):ResponseEntity<ErrorResponse>{
        return ResponseEntity(ErrorResponse(ex.message?:"Unknown error",HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserException::class)
    fun userExceptionHandler(ex: UserException):ResponseEntity<ErrorResponse>{
        return ResponseEntity(ErrorResponse(ex.message?:"Unknown error"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val result = ex.bindingResult
        val errors = result.fieldErrors
        var message = ""
        for (error in errors) {
            message += "${error.defaultMessage};"
        }
        return ResponseEntity(ErrorResponse(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception::class)
    fun generalExceptionHandler(ex: Exception):ResponseEntity<ErrorResponse>{
        return ResponseEntity(ErrorResponse(ex.message?:"Unknown error"), HttpStatus.BAD_REQUEST);
    }



}