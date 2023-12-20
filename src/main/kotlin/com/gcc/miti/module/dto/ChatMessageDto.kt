package com.gcc.miti.module.dto

import com.gcc.miti.module.entity.ChatMessage

data class ChatMessageDto(
    val userId: String,
    val nickname: String,
    val content: String,
) {
    companion object {
        fun chatMessageToDto(chatMessage: ChatMessage): ChatMessageDto {
            with(chatMessage) {
                return ChatMessageDto(
                    user.userId,
                    user.nickname,
                    content,
                )
            }
        }
    }
}
