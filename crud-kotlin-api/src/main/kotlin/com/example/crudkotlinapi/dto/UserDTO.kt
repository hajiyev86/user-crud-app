package com.example.crudkotlinapi.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class UserDTO(
        val id: Long?,
        @field:NotBlank(message =  "{validation.field.name.empty}")
        @field:Size(min = 3, message ="{validation.field.name.lessThan3Chars}")
        var name: String,
        @field:NotBlank(message ="{validation.field.email.empty}")
        @field:Email(message = "{validation.field.email.notValid}")
        val email: String,
        @field:NotBlank(message = "{validation.field.phoneNumber.empty}")
        @field:Size(min = 3, message = "{validation.field.phoneNumber.lessThan3Chars}")
        val phoneNumber: String,
        var age: Int?,
        var creationDate: String?
)