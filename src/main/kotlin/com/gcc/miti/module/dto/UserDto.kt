package com.gcc.miti.module.dto

import com.gcc.miti.module.constants.Gender
import com.gcc.miti.module.entity.PartyList
import com.gcc.miti.module.entity.User

data class UserDto(
    val description: String?,
    val gender: Gender,
    val userName: String,
    val password: String,
) {
    companion object {
        fun toPartyList(user: User): PartyList {
            return PartyList().also {
                it.partyList = listOf(user)
            }
        }
    }
}
