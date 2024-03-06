package com.gcc.miti.user.dto

import java.time.LocalDateTime

class BlockedUserOutput(
    val nickname: String,
    val userId: String,
    val createdDate: LocalDateTime?
)