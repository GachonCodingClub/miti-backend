package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.Certification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CertificationRepository : JpaRepository<Certification, String> {
    fun getByEmail(email: String): Certification?
}
