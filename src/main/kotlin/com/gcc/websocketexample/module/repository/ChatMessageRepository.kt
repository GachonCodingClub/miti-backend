package com.gcc.websocketexample.module.repository

import com.gcc.websocketexample.module.entity.ChatMessage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {
    fun findAllByGroup_Id(groupId: Long): List<ChatMessage>
}
