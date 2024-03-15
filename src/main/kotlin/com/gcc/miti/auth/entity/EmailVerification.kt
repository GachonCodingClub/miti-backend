package com.gcc.miti.auth.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "email_verification")
class EmailVerification(
    var randomNumber: String? = null,

    @Id
    val email: String,
) : BaseTimeEntity() {

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean = false

    fun isVerifiedInOneHour(): Boolean {
        return (this.isVerified && this.modifiedDate!!.plusHours(1).isAfter(
            LocalDateTime.now()
        ))
    }

    fun isVerificationSentIn15Minutes(): Boolean {
        return this.modifiedDate!!.plusMinutes(15).isAfter(LocalDateTime.now())
    }
}
