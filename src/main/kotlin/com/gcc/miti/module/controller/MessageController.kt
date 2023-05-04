package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.ChatMessageDto
import com.gcc.miti.module.repository.ChatMessageRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/message")
class MessageController(
    private val chatMessageRepository: ChatMessageRepository
) {
    @GetMapping("/{groupId}")
    fun getAllMessages(@PathVariable(name = "groupId") groupId: Long): List<ChatMessageDto> {
        return chatMessageRepository.findAllByGroup_Id(groupId).map {
            ChatMessageDto.chatMessageToDto(it)
        }
    }
}
