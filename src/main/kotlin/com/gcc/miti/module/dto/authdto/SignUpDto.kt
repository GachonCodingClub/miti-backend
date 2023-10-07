package com.gcc.miti.module.dto.authdto

import com.gcc.miti.module.constants.Gender
import com.gcc.miti.module.constants.Height
import com.gcc.miti.module.constants.Weight
import com.gcc.miti.module.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDate

data class SignUpDto(
    val userId: String,
    var password: String,
    val description: String?,
    val gender: Gender,
    val userName: String,
    val nickname: String,
    val height: Height,
    val weight: Weight,
    val birthDate: LocalDate,
) {
    fun toUser(passwordEncoder: PasswordEncoder): User {
        return User(
            passwordEncoder.encode(password),
            userName,
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
