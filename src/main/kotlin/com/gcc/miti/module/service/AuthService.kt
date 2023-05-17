package com.gcc.miti.module.service

import com.gcc.miti.module.entity.Verification
import com.gcc.miti.module.repository.UserRepository
import com.gcc.miti.module.repository.VerificationRepository
import com.gcc.miti.module.utils.MailUtils
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.mail.Message
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val verificationRepository: VerificationRepository,
    private val javaMailSender: JavaMailSender,
    private val mailUtils: MailUtils,
) {
    fun saveMail(email: String): Verification {
        val certificationNumber: String = mailUtils.randomNumber()
        sendMail(email, certificationNumber)
        return verificationRepository.save(Verification(certificationNumber, email))
    }

    fun getMailMessage(email: String, certificationNumber: String): MimeMessage {
        val message = javaMailSender.createMimeMessage()
        message.addRecipient(Message.RecipientType.TO, InternetAddress(email))
        message.subject = "[hannah-education] 본인 인증 메일"
        message.setText(certificationNumber.toString(), "UTF-8", "html")
        return message
    }

    @Async
    fun sendMail(email: String, randomnum: String) {
        val message = getMailMessage(email, randomnum)
        javaMailSender.send(message)
    }

    @Transactional
    fun checkCertification(email: String, certificationNumber: String): Boolean {
        val verification = verificationRepository.getByEmail(email)
        if (certificationNumber == verification.randomNumber) {
            verification.flag = true
        }
        return true
    }
}
