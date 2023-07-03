package com.gcc.miti.module.service

import com.gcc.miti.module.dto.authDto.SignInDto
import com.gcc.miti.module.dto.authDto.TokenDto
import com.gcc.miti.module.entity.RefreshToken
import com.gcc.miti.module.entity.Verification
import com.gcc.miti.module.global.exception.BaseException
import com.gcc.miti.module.global.exception.BaseExceptionCode
import com.gcc.miti.module.global.security.JwtTokenProvider
import com.gcc.miti.module.repository.RefreshTokenRepository
import com.gcc.miti.module.repository.VerificationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val verificationRepository: VerificationRepository,
    private val mailService: MailService,
    private val tokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
) {
    @Transactional
    fun saveMail(email: String): Verification {
        val certificationNumber: String = mailService.randomNumber()
        mailService.sendMail(email, certificationNumber)
        return verificationRepository.save(Verification(certificationNumber, email))
    }

    @Transactional
    fun checkCertification(email: String, certificationNumber: String): Boolean {
        val verification = verificationRepository.getByEmail(email)
        if (certificationNumber == verification.randomNumber) {
            verification.flag = true
            return true
        } else {
            return false
        }
    }

    fun signIn(signInDto: SignInDto): TokenDto {
        signInDto.apply {
            val credential = UsernamePasswordAuthenticationToken(userId, password)
            val authentication = authenticationManagerBuilder.`object`.authenticate(credential)
            val token = tokenProvider.createToken(authentication)
            refreshTokenRepository.save(RefreshToken(userId, token.refreshToken))
            return token
        }
    }

    fun refresh(tokenDto: TokenDto): TokenDto {
        val userId = tokenProvider.getUserPk(tokenDto.accessToken)
        val savedRefreshToken = refreshTokenRepository.findByIdOrNull(userId)
        if (tokenDto.refreshToken == savedRefreshToken?.refreshToken) {
            val authentication = tokenProvider.getAuthentication(tokenDto.accessToken)
            val token = tokenProvider.createToken(authentication)
            return TokenDto(token.accessToken, token.refreshToken).also {
                refreshTokenRepository.save(RefreshToken(userId, token.refreshToken))
            }
        } else {
            throw BaseException(BaseExceptionCode.REFRESH_TOKEN_MISMATCH)
        }
    }
}
