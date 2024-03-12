package com.gcc.miti.auth.controller

import com.gcc.miti.auth.dto.ChangePasswordRequest
import com.gcc.miti.auth.dto.SignInDto
import com.gcc.miti.auth.dto.SignUpDto
import com.gcc.miti.auth.dto.TokenDto
import com.gcc.miti.auth.service.AuthService
import com.gcc.miti.common.dto.ResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Auth", description = "계정 관련 API")
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {
    @PostMapping("/verification")
    @Operation(summary = "이메일 인증번호 전송")
    fun sendEmailVerification(@RequestParam email: String): Boolean {
        return authService.sendEmailVerification(email)
    }

    @PostMapping("/verification/password")
    @Operation(summary = "이메일 인증번호 전송 (비밀번호 변경 전용)")
    fun sendEmailVerificationForChangingPassword(@RequestParam email: String): Boolean {
        return authService.sendEmailVerificationForChangingPassword(email)
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경")
    fun changePassword(
        @RequestBody request: ChangePasswordRequest,
    ): Boolean {
        return authService.changePassword(request)
    }

    @PostMapping("/sign-up")
    @Operation(
        summary = "회원가입",
        description = "가입하기 전에 /auth/certification, /auth/certification/confirm을 통해서 인증을 완료한 상태에서만 가입이 가능 ",
    )
    fun signUp(@RequestBody signUpDto: SignUpDto): ResponseEntity<Boolean> {
        return ResponseEntity.ok().body(authService.signUp(signUpDto))
    }

    @PostMapping("/verification/confirm")
    @Operation(summary = "이메일 인증번호 검증")
    fun verifyVerificationNumber(
        @RequestParam email: String,
        @RequestParam verificationNumber: String,
    ): Boolean {
        return authService.verifyVerificationNumber(email, verificationNumber)
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
