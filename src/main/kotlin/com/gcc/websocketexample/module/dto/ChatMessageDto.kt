package com.gcc.websocketexample.module.dto

import com.gcc.websocketexample.module.entity.ChatMessage

data class ChatMessageDto(
    val userName: String,
    val content: String
) {
    companion object {
        fun chatMessageToDto(chatMessage: ChatMessage): ChatMessageDto {
            with(chatMessage) {
                return ChatMessageDto(
                    user.userName,
                    content
                )
            }
        }
    }
}
