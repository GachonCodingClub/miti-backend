package com.gcc.miti.module.dto

import com.gcc.miti.module.entity.User

data class GroupMembersDto(
    val userId: String,
    val description: String
) {
    companion object {
        fun userToGroupMembersDto(user: User): GroupMembersDto {
            return GroupMembersDto(
                user.userId,
                user.description
            )
        }
    }
}
