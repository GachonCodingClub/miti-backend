package com.gcc.miti.chat.controller

import com.gcc.miti.chat.dto.ChatMessageDto
import com.gcc.miti.auth.security.GetIdFromToken
import com.gcc.miti.chat.service.ChatMessageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/message")
class ChatMessageController(
    private val chatMessageService: ChatMessageService
) {

    @GetMapping("/{groupId}")
    fun getAllMessages(
        @PathVariable(name = "groupId") groupId: Long,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): List<ChatMessageDto> {
        return chatMessageService.getAllMessages(groupId, userId)
    }

    @GetMapping("/{groupId}/page")
    fun getAllMessagesPageable(
        @PathVariable(name = "groupId") groupId: Long,
        @PageableDefault(page = 0, size = 10) pageable: Pageable,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): List<ChatMessageDto> {
        return chatMessageService.getAllMessagesPageable(groupId, userId, pageable)
    }

    @Operation(summary = "채팅방 알림 갱신")
    @GetMapping("/{groupId}/refresh/last-read")
    fun refreshLastReadChatMessage(
        @PathVariable(name = "groupId") groupId: Long,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ) {
        return chatMessageService.refreshLastReadChatMessage(groupId, userId)
    }
}
