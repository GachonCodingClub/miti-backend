package com.gcc.miti.service

import org.springframework.beans.factory.annotation.Value
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
    @Value("\${spring.profiles.active}")
    lateinit var profile:String
    @Async
    fun sendMail(email: String, randomNum: String) {
        if(profile == "test"){
            return
        }
        val message = getMailMessage(email, randomNum)
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
