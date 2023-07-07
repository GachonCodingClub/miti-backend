package com.gcc.miti.module.dto

import com.gcc.miti.module.entity.ChatMessage

data class ChatMessageDto(
    val userName: String,
    val content: String,
) {
    companion object {
        fun chatMessageToDto(chatMessage: ChatMessage): ChatMessageDto {
            with(chatMessage) {
                return ChatMessageDto(
                    user.userName,
                    content,
                )
            }
        }
    }
}
