package com.gcc.miti.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class RefreshToken(
    @Id
    val userId: String,
    val refreshToken: String,
) : BaseTimeEntity()
