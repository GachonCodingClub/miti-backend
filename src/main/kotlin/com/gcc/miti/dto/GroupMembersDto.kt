package com.gcc.miti.dto

import com.gcc.miti.entity.User

data class GroupMembersDto(
    val userId: String,
    val description: String?,

) {
    companion object {
        fun userToGroupMembersDto(user: User): GroupMembersDto {
            return GroupMembersDto(
                user.userId,
                user.description,
            )
        }
    }
}
