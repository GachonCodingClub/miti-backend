package com.gcc.miti.repository

import com.gcc.miti.entity.Certification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CertificationRepository : JpaRepository<Certification, String> {
    fun getByEmail(email: String): Certification?
}
