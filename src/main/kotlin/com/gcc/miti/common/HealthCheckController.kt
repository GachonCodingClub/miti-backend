package com.gcc.miti.common


import com.gcc.miti.common.aop.NoLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@NoLogging
@RestController
class HealthCheckController{
    @GetMapping("/health")
    fun healthCheck(): String {
        return "ok"
    }
}