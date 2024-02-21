package com.gcc.miti.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "certification")
class Certification(
    var randomNumber: String? = null,

    @Id
    val email: String? = null,
) : BaseTimeEntity() {
    var flag: Boolean = false
}
