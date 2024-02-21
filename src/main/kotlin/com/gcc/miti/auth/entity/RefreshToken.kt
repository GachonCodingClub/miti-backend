package com.gcc.miti.auth.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class RefreshToken(
    @Id
    val userId: String,
    val refreshToken: String,
) : BaseTimeEntity()
