package com.gcc.miti.module.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "verification")
class Verification(
    val randomNumber: String? = null,

    @Id
    val email: String? = null,
) : BaseTimeEntity() {
    var flag: Boolean = false
}
