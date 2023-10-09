package com.example.crudkotlinapi.service

interface AgifyService {
    fun fetchAgeFromExternalApi(name: String): Int?;
}