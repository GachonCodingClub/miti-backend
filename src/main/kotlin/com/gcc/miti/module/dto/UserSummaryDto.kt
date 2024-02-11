package com.gcc.miti.module.dto

import com.gcc.miti.module.constants.Gender
import com.gcc.miti.module.constants.Height
import com.gcc.miti.module.constants.Weight
import com.gcc.miti.module.entity.User
import java.time.LocalDate

class UserSummaryDto(
    val userId: String,
    val userName: String,
    val gender: Gender,
    val height: Height,
    val weight: Weight,
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
                    height,
                    weight,
                    LocalDate.now().year - birthDate.year,
                    description,
                )
            }
        }
    }
}
