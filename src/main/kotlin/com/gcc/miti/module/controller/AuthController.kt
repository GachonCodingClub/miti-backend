package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.authdto.SignInDto
import com.gcc.miti.module.dto.authdto.SignUpDto
import com.gcc.miti.module.dto.authdto.TokenDto
import com.gcc.miti.module.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/certification")
    @Operation(summary = "이메일 인증번호 전송")
    fun sendEmailVerification(@RequestParam email: String): Boolean {
        return authService.saveMail(email)
    }

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입")
    fun signUp(@RequestBody signUpDto: SignUpDto): ResponseEntity<Boolean> {
        return ResponseEntity.ok().body(authService.signUp(signUpDto))
    }

    @PostMapping("/certification/confirm")
    @Operation(summary = "이메일 인증번호 검증")
    fun checkCertification(
        @RequestParam email: String,
        @RequestParam certificationNumber: String,
    ): Boolean {
        return authService.checkCertification(email, certificationNumber)
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인")
    fun signIn(@RequestBody signInDto: SignInDto): TokenDto {
        return authService.signIn(signInDto)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody tokenDto: TokenDto): TokenDto {
        return authService.refresh(tokenDto)
    }
}
