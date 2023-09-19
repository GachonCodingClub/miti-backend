package com.gcc.miti.module.service

import com.gcc.miti.module.dto.authdto.SignInDto
import com.gcc.miti.module.dto.authdto.SignUpDto
import com.gcc.miti.module.dto.authdto.TokenDto
import com.gcc.miti.module.entity.Certification
import com.gcc.miti.module.entity.RefreshToken
import com.gcc.miti.module.global.exception.BaseException
import com.gcc.miti.module.global.exception.BaseExceptionCode
import com.gcc.miti.module.global.security.JwtTokenProvider
import com.gcc.miti.module.repository.CertificationRepository
import com.gcc.miti.module.repository.RefreshTokenRepository
import com.gcc.miti.module.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class AuthService(
    private val certificationRepository: CertificationRepository,
    private val mailService: MailService,
    private val tokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun saveMail(email: String): Boolean {
        val certificationNumber: String = mailService.randomNumber()
        mailService.sendMail(email, certificationNumber)
        val verification = certificationRepository.getByEmail(email)
        return if (verification != null) {
            certificationRepository.save(
                verification.apply {
                    this.randomNumber = certificationNumber
                },
            )
            true
        } else {
            certificationRepository.save(Certification(certificationNumber, email))
            true
        }
    }

    @Transactional
    fun signUp(signUpDto: SignUpDto): Boolean {
        val certification =
            certificationRepository.getByEmail(signUpDto.userId) ?: throw BaseException(BaseExceptionCode.BAD_REQUEST)
        if (!certification.flag) throw BaseException(BaseExceptionCode.BAD_REQUEST)
        userRepository.save(signUpDto.toUser(passwordEncoder))
        return true
    }

    @Transactional
    fun checkCertification(email: String, certificationNumber: String): Boolean {
        val verification = certificationRepository.getByEmail(email)
        if (verification != null) {
            if (verification.modifiedDate!!.plusMinutes(1).isBefore(LocalDateTime.now())) {
                return if (certificationNumber == verification.randomNumber) {
                    verification.flag = true
                    true
                } else {
                    false
                }
            }
        }
        return false
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
