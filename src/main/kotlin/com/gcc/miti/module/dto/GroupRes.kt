package com.gcc.miti.module.dto

import com.gcc.miti.module.constants.GroupStatus
import java.time.LocalDateTime

data class GroupRes(
    val description: String,
    val title: String,
    val maxUsers: Int,
    val meetDate: LocalDateTime?,
    val meetPlace: String?,
    val leaderUserId: String,
    val groupStatus: GroupStatus,
)