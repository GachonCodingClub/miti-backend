package com.gcc.miti.user.repository

import com.gcc.miti.user.entity.BannedUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BannedUserRepository : JpaRepository<BannedUser, Long> {
    fun existsByEmail(email: String): Boolean
}