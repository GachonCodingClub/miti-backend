package com.gcc.miti.group.dto

import com.gcc.miti.user.entity.User

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
