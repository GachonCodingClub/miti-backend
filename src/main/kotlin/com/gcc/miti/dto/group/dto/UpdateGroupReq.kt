package com.gcc.miti.dto.group.dto

import java.time.LocalDateTime

class UpdateGroupReq(
    val description: String,
    val title: String,
    val meetDate: LocalDateTime?,
    val meetPlace: String?,
)