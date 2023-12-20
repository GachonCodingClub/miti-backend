package com.gcc.miti.module.global.security

import com.gcc.miti.module.global.exception.BaseException
import com.gcc.miti.module.global.exception.BaseExceptionCode
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

object SecurityUtils {
    fun getUserIdFromJwt(): String {
        val auth =
            SecurityContextHolder.getContext().authentication.principal as? UserDetails ?: throw BaseException(BaseExceptionCode.USER_NOT_FOUND)
        return auth.username
    }
}