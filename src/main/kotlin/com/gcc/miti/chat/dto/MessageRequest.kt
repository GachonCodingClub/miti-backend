package com.gcc.miti.chat.dto

import com.gcc.miti.user.entity.User
import java.time.LocalDateTime

data class MessageRequest(
    val message: String,
    val sender: String,
    val groupId: String,
) {
    fun toChatMessageDto(user: User): ChatMessageDto {
        return ChatMessageDto(
            user.userId,
            user.nickname,
            message,
            LocalDateTime.now(),
        )
    }
}
