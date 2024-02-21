package com.gcc.miti.chat.service

import com.gcc.miti.chat.dto.ChatMessageDto
import com.gcc.miti.chat.entity.ChatMessage
import com.gcc.miti.chat.entity.LastReadChatMessage
import com.gcc.miti.chat.repository.ChatMessageRepository
import com.gcc.miti.chat.repository.LastReadChatMessageRepository
import com.gcc.miti.group.entity.Group
import com.gcc.miti.group.repository.GroupRepository
import com.gcc.miti.user.entity.User
import com.gcc.miti.user.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatMessageService(
    private val groupRepository: GroupRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val lastReadChatMessageRepository: LastReadChatMessageRepository, private val userRepository: UserRepository
) {
    @Transactional
    fun getAllMessages(groupId: Long, userId: String): List<ChatMessageDto> {
        val group = groupRepository.getReferenceById(groupId)
        if (group.leader.userId != userId && !group.acceptedParties.flatMap { it.partyMember }
                .any { it.user?.userId == userId }) {
            return listOf()
        }
        return chatMessageRepository.findAllByGroup_IdOrderByCreatedAt(groupId).map {
            ChatMessageDto.chatMessageToDto(it)
        }
    }

    @Transactional
    fun getAllMessagesPageable(groupId: Long, userId: String, pageable: Pageable): List<ChatMessageDto> {
        val group = groupRepository.getReferenceById(groupId)
        val user = userRepository.getReferenceById(userId)
        if (group.leader.userId != userId && !group.acceptedParties.flatMap { it.partyMember }
                .any { it.user?.userId == userId }) {
            return listOf()
        }
        val chatMessages = chatMessageRepository.findAllByGroup_IdOrderByCreatedAtDesc(groupId, pageable)

        return chatMessages.map {
            ChatMessageDto.chatMessageToDto(it)
        }
    }

    @Transactional
    fun refreshLastReadChatMessage(groupId: Long, userId: String){
        val group = groupRepository.getReferenceById(groupId)
        val user = userRepository.getReferenceById(userId)
        val lastChatMessage = chatMessageRepository.findFirstByGroup_IdOrderByCreatedAtDesc(groupId) ?: return
        val lastReadChatMessage = lastReadChatMessageRepository.findByGroupAndUser(group, user)
        if(lastReadChatMessage == null){
            lastReadChatMessageRepository.save(LastReadChatMessage(
                user,
                group,
                lastChatMessage
            ))
        }else{
            lastReadChatMessage.chatMessage = lastChatMessage
        }
    }
}