package com.gcc.miti.module.service

import com.gcc.miti.module.entity.Verification
import com.gcc.miti.module.repository.VerificationRepository
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val verificationRepository: VerificationRepository,
    private val javaMailSender: JavaMailSender,
    private val mailService: MailService,
) {
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
}
