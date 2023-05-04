package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.MessageDto
import com.gcc.miti.module.entity.ChatMessage
import com.gcc.miti.module.repository.ChatMessageRepository
import com.gcc.miti.module.repository.UserRepository
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.transaction.Transactional

@Controller
@RequestMapping("/message")
class SocketController(
    private val simpMessagingTemplate: SimpMessagingTemplate,
    private val chatMessageRepository: ChatMessageRepository,
    private val userRepository: UserRepository
) {
    @MessageMapping("/send")
    @Transactional
    fun send(message: MessageDto) {
        chatMessageRepository.save(ChatMessage(userRepository.getReferenceById("test"), message.message))
        simpMessagingTemplate.convertAndSend("/topic/${message.roomId}", message.toDto())
    }
}
