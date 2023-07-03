package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.authDto.SignInDto
import com.gcc.miti.module.dto.authDto.TokenDto
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
    fun checkCertification(
        @PathVariable(name = "email") email: String,
        @RequestParam certificationNumber: String,
    ): Boolean {
        return authService.checkCertification(email, certificationNumber)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody signInDto: SignInDto): TokenDto {
        return authService.signIn(signInDto)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody tokenDto: TokenDto): TokenDto {
        return authService.refresh(tokenDto)
    }
}
