package com.example.crudkotlinapi.service

import com.example.crudkotlinapi.dto.AgifyResponseDTO
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AgifyServiceImpl(
        private val restTemplate: RestTemplate,
        @Value("\${external.agifyApi.url}") private val agifyApiUrl: String
):AgifyService {
    private val logger: Logger = LoggerFactory.getLogger(AgifyServiceImpl::class.java)

    @Cacheable(cacheNames = ["userAgeCache"])
    @CircuitBreaker(name = "agifyService", fallbackMethod = "fallbackFetchAge")
    override public fun fetchAgeFromExternalApi(name: String): Int? {
        val response = restTemplate.getForObject("$agifyApiUrl$name", AgifyResponseDTO::class.java)
        return response?.age
    }

    fun fallbackFetchAge(name: String, throwable: Throwable): Int? {
        logger.error("Circuit breaker opened for Agify API call: ${throwable.message}")
        return null;
    }
}