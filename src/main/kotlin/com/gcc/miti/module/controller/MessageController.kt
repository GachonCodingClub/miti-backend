package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.ChatMessageDto
import com.gcc.miti.module.repository.ChatMessageRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/message")
class MessageController(
    private val chatMessageRepository: ChatMessageRepository,
) {

    @GetMapping("/{groupId}")
    @Transactional(readOnly = true)
    fun getAllMessages(@PathVariable(name = "groupId") groupId: Long): List<ChatMessageDto> {
        return chatMessageRepository.findAllByGroup_IdOrderByCreatedAt(groupId).map {
            ChatMessageDto.chatMessageToDto(it)
        }
    }

    @GetMapping("/{groupId}/page")
    fun getAllMessagesPageable(@PathVariable(name = "groupId") groupId: Long,
    @PageableDefault(page = 0, size = 10) pageable: Pageable
    ): List<ChatMessageDto> {
        return chatMessageRepository.findAllByGroup_IdOrderByCreatedAtDesc(groupId, pageable).map {
            ChatMessageDto.chatMessageToDto(it)
        }
    }
}
