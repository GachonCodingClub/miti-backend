package com.gcc.miti.chat.controller

import com.gcc.miti.chat.dto.MessageDto
import com.gcc.miti.chat.entity.ChatMessage
import com.gcc.miti.chat.repository.ChatMessageRepository
import com.gcc.miti.group.repository.GroupRepository
import com.gcc.miti.notification.service.NotificationService
import com.gcc.miti.user.repository.UserRepository
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import javax.transaction.Transactional

@Controller
class SocketController(
    private val simpMessagingTemplate: SimpMessagingTemplate,
    private val chatMessageRepository: ChatMessageRepository,
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val notificationService: NotificationService,
) {
    @MessageMapping("/send")
    @Transactional
    fun send(message: MessageDto) {
        val group = groupRepository.getReferenceById(message.groupId.toLong())
        if (group.leader.userId != message.sender && !group.acceptedParties.flatMap { it.partyMember }
                .any { it.user?.userId == message.sender }) {
            return
        }
        val user = userRepository.getReferenceById(message.sender)
        val chatMessage = chatMessageRepository.save(
            ChatMessage(userRepository.getReferenceById(message.sender), message.message).also {
                it.group = group
            },
        )
        notificationService.sendNewChatNotification(chatMessage)
        simpMessagingTemplate.convertAndSend("/sub/${message.groupId}", message.toDto(user))
    }
}
