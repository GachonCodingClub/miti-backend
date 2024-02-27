package com.gcc.miti.group.dto

import com.gcc.miti.group.entity.Group
import com.gcc.miti.user.dto.UserSummaryDto
import java.time.LocalDateTime

class GroupListDto(
    val meetDate: LocalDateTime?,
    val meetPlace: String?,
    val maxUsers: Int,
    val nowUsers: Int,
    val title: String,
    val description: String,
    val id: Long,
    val unreadMessagesCount: Long,
    val leaderUserSummaryDto: UserSummaryDto,
    val isWaitingParty: Boolean,
) {
    companion object {
        fun toDto(group: Group, unreadMessagesCount: Long? = null, isLeader: Boolean = false): GroupListDto {
            with(group) {
                return GroupListDto(
                    meetDate,
                    meetPlace,
                    maxUsers,
                    countMembers,
                    title,
                    description,
                    id,
                    unreadMessagesCount ?: 0,
                    UserSummaryDto.toDto(leader),
                    isLeader && group.waitingParties.isNotEmpty()
                )
            }
        }
    }
}
