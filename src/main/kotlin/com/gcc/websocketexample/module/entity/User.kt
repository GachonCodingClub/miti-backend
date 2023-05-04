package com.gcc.websocketexample.module.entity

import com.gcc.websocketexample.module.constants.Gender
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    val password: String,
    val userName: String,
    val description: String,
    @Enumerated(value = EnumType.STRING)
    val gender: Gender
) {
    @Id
    val userId: String = ""
}
