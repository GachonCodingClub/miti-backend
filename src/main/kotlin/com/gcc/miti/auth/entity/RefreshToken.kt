package com.gcc.miti.auth.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class RefreshToken(
    @Id
    val userId: String,
    val refreshToken: String,
) : BaseTimeEntity()
