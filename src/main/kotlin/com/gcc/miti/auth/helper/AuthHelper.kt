package com.gcc.miti.auth.helper

import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AuthHelper {
    @Value("\${spring.profiles.active}")
    private lateinit var activeProfile: String

    fun isUniversityEmail(email: String) {
//        if (activeProfile == "develop") {
//            return
//        }
        if (!email.contains("@gachon.ac.kr")) {
            throw BaseException(BaseExceptionCode.NOT_UNIVERSITY_EMAIL)
        }
    }
}
