package com.gcc.miti.dto

import com.gcc.miti.constants.GroupStatus
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
