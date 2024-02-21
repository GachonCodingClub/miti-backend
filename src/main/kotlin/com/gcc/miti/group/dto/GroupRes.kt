package com.gcc.miti.group.dto

import com.gcc.miti.user.dto.UserSummaryDto
import com.gcc.miti.group.constants.GroupStatus
import java.time.LocalDateTime

data class GroupRes(
    val description: String,
    val title: String,
    val maxUsers: Int,
    val meetDate: LocalDateTime?,
    val meetPlace: String?,
    val leaderUserSummaryDto: UserSummaryDto,
    val groupStatus: GroupStatus,
)
