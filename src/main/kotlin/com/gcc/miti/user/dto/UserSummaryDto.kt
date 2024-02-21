package com.gcc.miti.user.dto

import com.gcc.miti.user.constants.Gender
import com.gcc.miti.user.entity.User
import java.time.LocalDate

class UserSummaryDto(
    val userId: String,
    val userName: String,
    val gender: Gender,
    val height: String,
    val weight: String,
    val age: Int,
    val description: String?,
) {
    companion object {
        fun toDto(user: User): UserSummaryDto {
            with(user) {
                return UserSummaryDto(
                    userId,
                    userName,
                    gender,
                    height.toMinMaxString(),
                    weight.toMinMaxString(),
                    LocalDate.now().year - birthDate.year,
                    description,
                )
            }
        }
    }
}
