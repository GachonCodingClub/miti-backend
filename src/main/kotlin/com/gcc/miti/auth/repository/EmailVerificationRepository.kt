package com.gcc.miti.auth.repository

import com.gcc.miti.auth.entity.EmailVerification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailVerificationRepository : JpaRepository<EmailVerification, String> {
    fun getByEmail(email: String): EmailVerification?
}
