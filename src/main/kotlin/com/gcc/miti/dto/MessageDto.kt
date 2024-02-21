package com.gcc.miti.dto

import com.gcc.miti.entity.User
import java.time.LocalDateTime

data class MessageDto(
    val message: String,
    val sender: String,
    val groupId: String,
) {
    fun toDto(user: User): ChatMessageDto {
        return ChatMessageDto(
            user.userId,
            user.nickname,
            message,
            LocalDateTime.now(),
        )
    }
}