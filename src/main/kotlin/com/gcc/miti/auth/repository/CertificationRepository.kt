package com.gcc.miti.auth.repository

import com.gcc.miti.auth.entity.Certification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CertificationRepository : JpaRepository<Certification, String> {
    fun getByEmail(email: String): Certification?
}
