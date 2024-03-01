package com.gcc.miti.auth.dto

import com.gcc.miti.user.constants.Gender
import com.gcc.miti.user.constants.Height
import com.gcc.miti.user.constants.Weight
import com.gcc.miti.user.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

data class SignUpDto(
    val userId: String,
    var password: String,
    val description: String?,
    val gender: Gender,
    val nickname: String,
    val height: Height,
    val weight: Weight,
    val birthDate: LocalDate,
) {
    fun toUser(passwordEncoder: PasswordEncoder): User {
        return User(
            passwordEncoder.encode(password),
            description,
            gender,
            height,
            weight,
            nickname,
            birthDate,
        ).also {
            it.userId = userId
        }
    }
}
