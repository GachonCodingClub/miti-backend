package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.ResponseDto
import com.gcc.miti.module.dto.authdto.SignInDto
import com.gcc.miti.module.dto.authdto.SignUpDto
import com.gcc.miti.module.dto.authdto.TokenDto
import com.gcc.miti.module.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
    @Operation(
        summary = "회원가입",
        description = "가입하기 전에 /auth/certification, /auth/certification/confirm을 통해서 인증을 완료한 상태에서만 가입이 가능 ",
    )
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

    @Operation(summary = "닉네임 중복 검사")
    @GetMapping("/check/nickname")
    fun checkNickname(@RequestParam nickname: String): ResponseDto<Boolean> {
        return ResponseDto(authService.checkNicknameExists(nickname))
    }

//    @PostMapping("/refresh")
//    fun refresh(@RequestBody tokenDto: TokenDto, authentication: Authentication): TokenDto {
//        return authService.refresh(tokenDto)
//    }
}
