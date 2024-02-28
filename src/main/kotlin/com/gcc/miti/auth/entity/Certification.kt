package com.gcc.miti.auth.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "certification")
class Certification(
    var randomNumber: String? = null,

    @Id
    val email: String,
) : BaseTimeEntity() {
    var flag: Boolean = false
}
