package com.gcc.miti.module.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "verification")
class Verification(
    val randomNumber: String,
    var flag: Boolean,

    @Id
    val email: String,
)
