package com.gcc.miti.chat.service

import com.gcc.miti.chat.dto.ChatMessageDto
import com.gcc.miti.chat.entity.LastReadChatMessage
import com.gcc.miti.chat.repository.ChatMessageRepository
import com.gcc.miti.chat.repository.LastReadChatMessageRepository
import com.gcc.miti.group.repository.GroupRepository
import com.gcc.miti.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Service
class ChatMessageService(
    private val groupRepository: GroupRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val lastReadChatMessageRepository: LastReadChatMessageRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun getAllMessages(groupId: Long, userId: String): List<ChatMessageDto> {
        val group = groupRepository.getReferenceById(groupId)
        if (!group.isGroupMember(userId)) return emptyList()
        return chatMessageRepository.findAllByGroup_IdOrderByCreatedAt(groupId).map {
            ChatMessageDto.chatMessageToDto(it)
        }
    }

    @Transactional
    @Cacheable("chatMessages", key = "#groupId", condition = "#pageable.pageSize == 1")
    fun getAllMessagesPageable(groupId: Long, userId: String, pageable: Pageable): List<ChatMessageDto> {
        val group = groupRepository.getReferenceById(groupId)
        if (!group.isGroupMember(userId)) return emptyList()
        val chatMessages = chatMessageRepository.findAllByGroup_IdOrderByCreatedAtDesc(groupId, pageable)
        return chatMessages.map {
            ChatMessageDto.chatMessageToDto(it)
        }
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    @CacheEvict("chatMessages", allEntries = true)
    fun removeSize1ChatMessageCaches() {
    }

    @Transactional
    fun refreshLastReadChatMessage(groupId: Long, userId: String) {
        try {
            val group = groupRepository.getReferenceById(groupId)
            val user = userRepository.getReferenceById(userId)
            val lastChatMessage = chatMessageRepository.findFirstByGroup_IdOrderByCreatedAtDesc(groupId) ?: return
            val lastReadChatMessage = lastReadChatMessageRepository.findByGroupAndUser(group, user)
            if (lastReadChatMessage == null) {
                lastReadChatMessageRepository.save(
                    LastReadChatMessage(
                        user,
                        group,
                        lastChatMessage
                    )
                )
            } else {
                lastReadChatMessage.chatMessage = lastChatMessage
            }
        } catch (e: Exception) {
            if (e.message?.contains(LastReadChatMessage.LAST_READ_CHAT_MESSAGE_UNIQUE_INDEX_NAME) == true) {
                return
            }
            throw e
        }
    }
}