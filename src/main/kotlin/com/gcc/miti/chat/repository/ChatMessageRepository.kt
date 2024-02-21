package com.gcc.miti.chat.repository

import com.gcc.miti.chat.entity.ChatMessage
import com.gcc.miti.group.entity.Group
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {
    fun findAllByGroup_IdOrderByCreatedAt(groupId: Long): List<ChatMessage>

    fun findAllByGroup_IdOrderByCreatedAtDesc(groupId: Long, pageable: Pageable): List<ChatMessage>

    fun findFirstByGroup_IdOrderByCreatedAtDesc(groupId: Long): ChatMessage?

    fun countByGroupAndIdGreaterThan(group: Group, id: Long): Long
}
