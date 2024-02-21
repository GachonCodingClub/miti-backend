package com.gcc.miti.user.dto

import com.gcc.miti.user.constants.Gender
import com.gcc.miti.user.entity.User

class ProfileRes(
    val nickname: String,
    val gender: Gender,
    val height: String?,
    val weight: String?,
    val age: Int,
    val description: String?,
) {
    companion object {
        fun userToProfileRes(user: User): ProfileRes {
            with(user) {
                return ProfileRes(
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