package com.example.crudkotlinapi.dto

data class UserSearchPageableRequestDTO(
        var page: Int? = null,
        var rowSizePerPage: Int? = null,
        var sort: String? = null,
        var order: String? = null,
        var userId: Long? = null,
        var email: String? = null,
        var name: String? = null,
        var phoneNumber: String? = null
)
