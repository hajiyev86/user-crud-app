package com.example.crudkotlinapi.dto

import java.io.Serializable

data class PageableResponseDTO<T>(
        var total: Long = 0L,
        var rows: List<T>? = null
) : Serializable
