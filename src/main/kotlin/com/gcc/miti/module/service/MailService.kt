package com.gcc.miti.module.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.security.SecureRandom
import javax.mail.Message
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class MailService(
    private val javaMailSender: JavaMailSender,
) {
    @Async
    fun sendMail(email: String, randomnum: String) {
        val message = getMailMessage(email, randomnum)
        javaMailSender.send(message)
    }
    fun getMailMessage(email: String, certificationNumber: String): MimeMessage {
        val message = javaMailSender.createMimeMessage()
        message.addRecipient(Message.RecipientType.TO, InternetAddress(email))
        message.subject = "[hannah-education] 본인 인증 메일"
        message.setText(certificationNumber.toString(), "UTF-8", "html")
        return message
    }
    fun randomNumber(): String {
        val secureRandom = SecureRandom()
        return secureRandom.nextInt(100000, 999999).toString()
    }
}
