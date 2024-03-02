package com.gcc.miti.auth.helper

import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import org.springframework.stereotype.Component

@Component
class AuthHelper {
    fun isUniversityEmail(email: String) {
        if (!email.contains("@gachon.ac.kr")) {
            throw BaseException(BaseExceptionCode.NOT_UNIVERSITY_EMAIL)
        }
    }
}
