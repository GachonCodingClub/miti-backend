package com.gcc.miti.module.dto.user.dto

import com.gcc.miti.module.constants.Gender
import com.gcc.miti.module.entity.User

class ProfileRes(
    val nickname: String,
    val gender: Gender,
    val height: String?,
    val weight: String?,
    val age: Int,
    val description: String?,
){
    companion object{
        fun userToProfileRes(user: User): ProfileRes{
            with(user){
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