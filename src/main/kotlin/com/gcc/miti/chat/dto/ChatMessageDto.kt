package com.gcc.miti.chat.dto

import com.gcc.miti.chat.entity.ChatMessage
import java.time.LocalDateTime

data class ChatMessageDto(
    val userId: String,
    val nickname: String,
    val content: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun chatMessageToDto(chatMessage: ChatMessage): ChatMessageDto {
            with(chatMessage) {
                return ChatMessageDto(
                    user.userId,
                    user.nickname,
                    content,
                    createdAt ?: LocalDateTime.now()
                )
            }
        }
    }
}
