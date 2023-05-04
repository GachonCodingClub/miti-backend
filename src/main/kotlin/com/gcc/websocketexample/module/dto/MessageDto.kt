package com.gcc.websocketexample.module.dto

data class MessageDto(
    val message: String,
    val sender: String,
    val roomId: String
) {
    fun toDto(): ChatMessageDto {
        return ChatMessageDto(
            sender,
            message
        )
    }
}
