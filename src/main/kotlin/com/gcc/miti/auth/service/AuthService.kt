package com.gcc.miti.auth.service

import com.gcc.miti.auth.dto.ChangePasswordRequest
import com.gcc.miti.auth.dto.SignInRequest
import com.gcc.miti.auth.dto.SignUpRequest
import com.gcc.miti.auth.dto.TokenResponse
import com.gcc.miti.auth.entity.EmailVerification
import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import com.gcc.miti.auth.security.JwtTokenProvider
import com.gcc.miti.auth.utils.EmailUtils
import com.gcc.miti.auth.repository.EmailVerificationRepository
import com.gcc.miti.user.repository.BannedUserRepository
import com.gcc.miti.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val emailVerificationRepository: EmailVerificationRepository,
    private val mailService: MailService,
    private val tokenProvider: JwtTokenProvider,
//    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val bannedUserRepository: BannedUserRepository
) {
    @Transactional
    fun sendEmailVerification(email: String): Boolean {
        checkBannedUserAndThrow(email)
        if (userRepository.existsById(email)) {
            throw BaseException(BaseExceptionCode.ALREADY_REGISTERED)
        }
        EmailUtils.isUniversityEmail(email)
        val randomNumber: String = mailService.randomNumber()
        mailService.sendMail(email, randomNumber)
        emailVerificationRepository.save(EmailVerification(randomNumber, email))
        return true
    }

    @Transactional
    fun sendEmailVerificationForChangingPassword(email: String): Boolean {
        if (!userRepository.existsById(email)) {
            throw BaseException(BaseExceptionCode.NOT_FOUND)
        }
        EmailUtils.isUniversityEmail(email)
        val randomNumber: String = mailService.randomNumber()
        mailService.sendMail(email, randomNumber)
        emailVerificationRepository.save(EmailVerification(randomNumber, email))
        return true
    }

    @Transactional
    fun signUp(signUpRequest: SignUpRequest): Boolean {
        checkBannedUserAndThrow(signUpRequest.userId)
        if (userRepository.existsById(signUpRequest.userId)) {
            throw BaseException(BaseExceptionCode.USER_ID_CONFLICT)
        }
        val emailVerification =
            emailVerificationRepository.getByEmail(signUpRequest.userId)
                ?: throw BaseException(BaseExceptionCode.NOT_VERIFIED)

        if (!emailVerification.isVerifiedInOneHour()) {
            throw BaseException(BaseExceptionCode.NOT_VERIFIED)
        }

        userRepository.save(signUpRequest.toUser(passwordEncoder))
        emailVerification.isVerified = false
        return true
    }

    @Transactional
    fun verifyVerificationNumber(email: String, verificationNumber: String): Boolean {
        val emailVerification = emailVerificationRepository.getByEmail(email)
        if (emailVerification != null) {
            if (emailVerification.isVerificationSentIn15Minutes()) {
                return if (verificationNumber == emailVerification.randomNumber) {
                    emailVerification.isVerified = true
                    true
                } else {
                    false
                }
            }
        }
        return false
    }

    fun checkNicknameExists(nickname: String): Boolean {
        if (userRepository.existsByNickname(nickname)) {
            throw BaseException(BaseExceptionCode.NICKNAME_CONFLICT)
        } else {
            return false
        }
    }

    @Transactional
    fun changePassword(request: ChangePasswordRequest): Boolean {
        val emailVerification =
            emailVerificationRepository.getByEmail(request.email) ?: throw BaseException(BaseExceptionCode.NOT_FOUND)
        if (emailVerification.randomNumber == request.verificationNumber) {
            val user = userRepository.findByIdOrNull(request.email) ?: throw BaseException(BaseExceptionCode.NOT_FOUND)
            user.password = passwordEncoder.encode(request.newPassword)
            return true
        }
        return false
    }

    fun signIn(signInRequest: SignInRequest): TokenResponse {
        with(signInRequest) {
            val credential = UsernamePasswordAuthenticationToken(userId, password)
            val authentication = authenticationManagerBuilder.`object`.authenticate(credential)
            val token = tokenProvider.createToken(authentication)
//            refreshTokenRepository.save(RefreshToken(userId, token.refreshToken))
            return token
        }
    }

    private fun checkBannedUserAndThrow(email: String) {
        if (bannedUserRepository.existsByEmail(email)) throw BaseException(BaseExceptionCode.BANNED_USER)
    }

//    fun refresh(tokenDto: TokenResponse): TokenResponse {
//        val userId = tokenProvider.getUserPk(tokenDto.accessToken)
//        val savedRefreshToken = refreshTokenRepository.findByIdOrNull(userId)
//        if (tokenDto.refreshToken == savedRefreshToken?.refreshToken) {
//            val authentication = tokenProvider.getAuthentication(tokenDto.accessToken)
//            val token = tokenProvider.createToken(authentication)
//            return TokenResponse(token.accessToken, token.refreshToken).also {
//                refreshTokenRepository.save(RefreshToken(userId, token.refreshToken))
//            }
//        } else {
//            throw BaseException(BaseExceptionCode.REFRESH_TOKEN_MISMATCH)
//        }
//    }
}
