package com.gcc.miti.auth.utils

import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode

object EmailUtils {
    const val GACHON_EMAIL_SUFFIX = "@gachon.ac.kr"
    fun isUniversityEmail(email: String) {
        if (!email.endsWith(GACHON_EMAIL_SUFFIX)) {
            throw BaseException(BaseExceptionCode.NOT_UNIVERSITY_EMAIL)
        }
    }
}
