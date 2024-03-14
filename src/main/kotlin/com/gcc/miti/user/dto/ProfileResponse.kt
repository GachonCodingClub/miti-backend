package com.gcc.miti.user.dto

import com.gcc.miti.user.constants.Gender
import com.gcc.miti.user.entity.User

class ProfileResponse(
    val nickname: String,
    val gender: Gender,
    val height: String?,
    val weight: String?,
    val age: Int,
    val description: String?,
) {
    companion object {
        fun toProfileResponse(user: User): ProfileResponse {
            with(user) {
                return ProfileResponse(
                    nickname,
                    gender,
                    height = height.toMinMaxString(),
                    weight = weight.toMinMaxString(),
                    age,
                    description
                )
            }

        }
    }
}