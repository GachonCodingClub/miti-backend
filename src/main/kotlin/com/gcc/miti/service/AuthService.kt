package com.gcc.miti.service

import com.gcc.miti.dto.authdto.ChangePasswordRequest
import com.gcc.miti.dto.authdto.SignInDto
import com.gcc.miti.dto.authdto.SignUpDto
import com.gcc.miti.dto.authdto.TokenDto
import com.gcc.miti.entity.Certification
import com.gcc.miti.global.exception.BaseException
import com.gcc.miti.global.exception.BaseExceptionCode
import com.gcc.miti.global.security.JwtTokenProvider
import com.gcc.miti.helper.AuthHelper
import com.gcc.miti.repository.CertificationRepository
import com.gcc.miti.repository.UserRepository
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
//    private val refreshTokenRepository: RefreshTokenRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authHelper: AuthHelper,
) {
    @Transactional
    fun saveMail(email: String): Boolean {
        if (userRepository.existsById(email)) {
            throw BaseException(BaseExceptionCode.ALREADY_REGISTERED)
        }
        authHelper.isUniversityEmail(email)
        val certificationNumber: String = mailService.randomNumber()
        mailService.sendMail(email, certificationNumber)
        val certification = certificationRepository.getByEmail(email)
        return if (certification != null) {
            certificationRepository.save(
                certification.apply {
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
    fun sendEmailCertificationForChangingPassword(email: String): Boolean {
        if (!userRepository.existsById(email)) {
            throw BaseException(BaseExceptionCode.NOT_FOUND)
        }
        authHelper.isUniversityEmail(email)
        val certificationNumber: String = mailService.randomNumber()
        mailService.sendMail(email, certificationNumber)
        val certification = certificationRepository.getByEmail(email)
        return if (certification != null) {
            certificationRepository.save(
                certification.apply {
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
        if (userRepository.existsById(signUpDto.userId)) {
            throw BaseException(BaseExceptionCode.USER_ID_CONFLICT)
        }
        val certification =
            certificationRepository.getByEmail(signUpDto.userId) ?: throw BaseException(BaseExceptionCode.NOT_CERTIFIED)
        if (!certification.flag || certification.modifiedDate!!.plusHours(1).isBefore(
                LocalDateTime.now(),
            )
        ) {
            throw BaseException(BaseExceptionCode.NOT_CERTIFIED)
        }
        userRepository.save(signUpDto.toUser(passwordEncoder))
        certification.flag = false
        return true
    }

    @Transactional
    fun checkCertification(email: String, certificationNumber: String): Boolean {
        val certification = certificationRepository.getByEmail(email)
        if (certification != null) {
            if (certification.modifiedDate!!.plusMinutes(3).isAfter(LocalDateTime.now())) {
                return if (certificationNumber == certification.randomNumber) {
                    certification.flag = true
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
        val certification =
            certificationRepository.getByEmail(request.email) ?: throw BaseException(BaseExceptionCode.NOT_FOUND)
        if (certification.randomNumber == request.certificationNumber) {
            val user = userRepository.findByIdOrNull(request.email) ?: throw BaseException(BaseExceptionCode.NOT_FOUND)
            user.password = passwordEncoder.encode(request.newPassword)
            return true
        }
        return false
    }

    fun signIn(signInDto: SignInDto): TokenDto {
        signInDto.apply {
            val credential = UsernamePasswordAuthenticationToken(userId, password)
            val authentication = authenticationManagerBuilder.`object`.authenticate(credential)
            val token = tokenProvider.createToken(authentication)
//            refreshTokenRepository.save(RefreshToken(userId, token.refreshToken))
            return token
        }
    }

//    fun refresh(tokenDto: TokenDto): TokenDto {
//        val userId = tokenProvider.getUserPk(tokenDto.accessToken)
//        val savedRefreshToken = refreshTokenRepository.findByIdOrNull(userId)
//        if (tokenDto.refreshToken == savedRefreshToken?.refreshToken) {
//            val authentication = tokenProvider.getAuthentication(tokenDto.accessToken)
//            val token = tokenProvider.createToken(authentication)
//            return TokenDto(token.accessToken, token.refreshToken).also {
//                refreshTokenRepository.save(RefreshToken(userId, token.refreshToken))
//            }
//        } else {
//            throw BaseException(BaseExceptionCode.REFRESH_TOKEN_MISMATCH)
//        }
//    }
}