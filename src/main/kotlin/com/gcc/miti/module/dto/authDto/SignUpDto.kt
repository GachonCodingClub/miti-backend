package com.gcc.miti.module.dto.authDto

import com.gcc.miti.module.constants.Gender
import com.gcc.miti.module.entity.User
import org.springframework.security.crypto.password.PasswordEncoder

data class SignUpDto(
    val userId: String,
    val password: String,
    val description: String?,
    val gender: Gender,
    val userName: String,
) {
    fun toUser(passwordEncoder: PasswordEncoder): User {
        return User(
            passwordEncoder.encode(password),
            userName,
            description,
            gender,
        ).also {
            it.userId = userId
        }
    }
}