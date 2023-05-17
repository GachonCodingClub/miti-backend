package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.ChatMessage
import com.gcc.miti.module.entity.Verification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VerificationRepository : JpaRepository<Verification, String> {
    fun getByEmail(email: String): Verification

}
