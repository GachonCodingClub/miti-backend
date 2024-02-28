package com.gcc.miti.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailSenderConfiguration {
    @Bean
    fun javaMailSender(): JavaMailSender {
        return JavaMailSenderImpl()
    }
}