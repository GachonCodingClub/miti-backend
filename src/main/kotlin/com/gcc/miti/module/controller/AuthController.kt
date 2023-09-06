package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.authdto.SignInDto
import com.gcc.miti.module.dto.authdto.SignUpDto
import com.gcc.miti.module.dto.authdto.TokenDto
import com.gcc.miti.module.entity.Verification
import com.gcc.miti.module.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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

    @PostMapping("/sign-up")
    fun signUp(@RequestBody signUpDto: SignUpDto): ResponseEntity<Boolean> {
        return ResponseEntity.ok().body(authService.signUp(signUpDto))
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
