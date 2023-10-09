package com.example.crudkotlinapi.config

import org.springframework.cache.Cache
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.cache.support.AbstractCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomCacheConfig {
    @Bean
    fun cacheManager(): ConcurrentMapCacheManager {
        return object : ConcurrentMapCacheManager() {
            override fun getCache(cacheName: String): Cache {
                return NonNullResultCache(super.getCache(cacheName)!!)
            }
        }
    }
}

class NonNullResultCache(private val delegate: Cache) : Cache by delegate {
    override fun put(key: Any, value: Any?) {
        if (value != null) {
            delegate.put(key, value)
        }
    }
}
