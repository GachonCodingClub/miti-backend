package com.gcc.miti.module.dto

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
