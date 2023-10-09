package com.example.crudkotlinapi.utils.exceptions

import org.springframework.http.HttpStatus

data class ErrorResponse(
        val message: String,
        val status: HttpStatus = HttpStatus.BAD_REQUEST,
        val code: Int = status.value()
)
