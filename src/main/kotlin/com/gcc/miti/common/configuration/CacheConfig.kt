package com.gcc.miti.common.configuration

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@EnableCaching
class CacheConfig {
    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = ConcurrentMapCacheManager()
        cacheManager.isAllowNullValues = false
        cacheManager.setCacheNames(listOf("group"))
        return cacheManager
    }
}