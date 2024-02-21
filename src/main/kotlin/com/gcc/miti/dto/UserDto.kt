package com.gcc.miti.dto

import com.gcc.miti.constants.Gender

data class UserDto(
    val password: String,
    val userName: String,
    val description: String?,
    val gender: Gender,
    val userId: String,
)
//    companion object {
//        fun toUser(userDto: UserDto, partyList: PartyList): User {
//            return User(
//                userDto.password,
//                userDto.userName,
//                userDto.description,
//                userDto.gender,
//            ).also {
//                it.userId = userDto.userId
//                it.partylist = partyList
//            }
//        }

//        fun toPartyList(user: User): PartyList {
//            return PartyList().also {
//                it.users = listOf(user)
//            }
