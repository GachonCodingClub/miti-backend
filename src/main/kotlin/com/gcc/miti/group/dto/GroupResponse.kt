package com.gcc.miti.group.dto

import com.gcc.miti.user.dto.UserSummaryDto
import com.gcc.miti.group.constants.GroupStatus
import com.gcc.miti.group.entity.Group
import java.time.LocalDateTime

data class GroupResponse(
    val description: String,
    val title: String,
    val maxUsers: Int,
    val meetDate: LocalDateTime?,
    val meetPlace: String?,
    val leaderUserSummaryDto: UserSummaryDto,
    val groupStatus: GroupStatus,
) {
    companion object {
        fun toGroupResponse(group: Group): GroupResponse {
            return GroupResponse(
                description = group.description,
                title = group.title,
                maxUsers = group.maxUsers,
                meetDate = group.meetDate,
                meetPlace = group.meetPlace,
                leaderUserSummaryDto = UserSummaryDto.toDto(group.leader),
                groupStatus = group.groupStatus,
            )
        }
    }
}
