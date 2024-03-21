package com.gcc.miti.auth.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class RefreshToken(
    @Id
    val userId: String,

    @Column(name = "refresh_token")
    val refreshToken: String,
) : BaseTimeEntity()
