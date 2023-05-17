package com.gcc.miti.module.utils

import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
class MailUtils() {
    fun randomNumber(): String {
        val secureRandom = SecureRandom()
        return secureRandom.nextInt(9999).toString()
    }
}
