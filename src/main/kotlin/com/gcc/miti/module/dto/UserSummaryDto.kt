package com.gcc.miti.module.dto

import com.gcc.miti.module.constants.Gender
import com.gcc.miti.module.constants.Height
import com.gcc.miti.module.constants.Weight
import com.gcc.miti.module.entity.PartyMember
import java.time.LocalDate

class UserSummaryDto(
    val userId: String,
    val userName: String,
    val gender: Gender,
    val height: Height,
    val weight: Weight,
    val age: Int,
) {
    companion object {
        fun toDto(partyMember: PartyMember): UserSummaryDto {
            with(partyMember.user!!) {
                return UserSummaryDto(
                    userId,
                    userName,
                    gender,
                    height,
                    weight,
                    LocalDate.now().year - birthDate.year,

                )
            }
        }
    }
}
