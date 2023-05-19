package com.gcc.miti.module.controller

import com.gcc.miti.module.entity.Verification
import com.gcc.miti.module.service.AuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {
    @GetMapping("")
    fun getMailAddress(@RequestParam email: String): Verification {
        return authService.saveMail(email)
    }

    @GetMapping("{email}")
    fun checkCertification(@PathVariable(name = "email") email: String, @RequestParam certificationNumber: String):Boolean {
        return authService.checkCertification(email, certificationNumber)
    }
}
