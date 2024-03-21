package com.gcc.miti.auth.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.security.SecureRandom
import jakarta.mail.Message
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage

@Service
class MailService(
    private val javaMailSender: JavaMailSender,
) {
    @Value("\${spring.profiles.active}")
    lateinit var profile:String
    @Async
    fun sendMail(email: String, randomNum: String) {
        if(profile == "test"){
            return
        }
        val message: MimeMessage = getMailMessage(email, randomNum)
        javaMailSender.send(message)
    }

    fun getMailMessage(email: String, certificationNumber: String): MimeMessage {
        val message = javaMailSender.createMimeMessage()
        message.addRecipient(Message.RecipientType.TO, InternetAddress(email))
        message.subject = "[미티] 본인 인증 메일"
        message.setText(certificationNumber, "UTF-8", "html")
        return message
    }

    fun randomNumber(): String {
        val secureRandom = SecureRandom()
        return secureRandom.nextInt(100000, 999999).toString()
    }
}
