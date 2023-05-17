package com.gcc.miti.module.controller

import com.gcc.miti.module.entity.Verification
import com.gcc.miti.module.service.AuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {
    @GetMapping("")
    fun getMailAddress(@RequestParam email: String): Verification {
        return authService.saveMail(email)
    }
}
