package com.gcc.miti.chat.controller

import com.gcc.miti.chat.dto.ChatMessageDto
import com.gcc.miti.auth.security.GetIdFromToken
import com.gcc.miti.chat.repository.ChatMessageRepository
import com.gcc.miti.group.repository.GroupRepository
import io.swagger.v3.oas.annotations.Parameter
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
    private val groupRepository: GroupRepository
) {

    @GetMapping("/{groupId}")
    @Transactional(readOnly = true)
    fun getAllMessages(
        @PathVariable(name = "groupId") groupId: Long,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): List<ChatMessageDto> {
        val group = groupRepository.getReferenceById(groupId)
        if (group.leader.userId != userId && !group.acceptedParties.flatMap { it.partyMember }
                .any { it.user?.userId == userId }) {
            return listOf()
        }
        return chatMessageRepository.findAllByGroup_IdOrderByCreatedAt(groupId).map {
            ChatMessageDto.chatMessageToDto(it)
        }
    }

    @GetMapping("/{groupId}/page")
    @Transactional(readOnly = true)
    fun getAllMessagesPageable(
        @PathVariable(name = "groupId") groupId: Long,
        @PageableDefault(page = 0, size = 10) pageable: Pageable,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): List<ChatMessageDto> {
        val group = groupRepository.getReferenceById(groupId)
        if (group.leader.userId != userId && !group.acceptedParties.flatMap { it.partyMember }
                .any { it.user?.userId == userId }) {
            return listOf()
        }
        return chatMessageRepository.findAllByGroup_IdOrderByCreatedAtDesc(groupId, pageable).map {
            ChatMessageDto.chatMessageToDto(it)
        }
    }
}
