package com.gcc.miti.group.dto

import com.gcc.miti.group.entity.Group
import java.time.LocalDateTime

class GroupListDto(
    val meetDate: LocalDateTime?,
    val meetPlace: String?,
    val maxUsers: Int,
    val nowUsers: Int,
    val title: String,
    val description: String,
    val id: Long,
    val unreadMessages: Long,
) {
    companion object {
        fun toDto(group: Group, unreadMessages: Long? = null): GroupListDto {
            with(group) {
                return GroupListDto(
                    meetDate,
                    meetPlace,
                    maxUsers,
                    countMembers,
                    title,
                    description,
                    id,
                    unreadMessages ?: 0
                )
            }
        }
    }
}
