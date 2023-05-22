package com.gcc.miti.module.entity

import com.gcc.miti.module.constants.Gender
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    val password: String,
    val userName: String,
    val description: String,
    @Enumerated(value = EnumType.STRING)
    val gender: Gender,
) : BaseTimeEntity() {
    @Id
    val userId: String = ""
}
