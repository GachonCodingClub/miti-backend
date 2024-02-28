package com.gcc.miti.chat.entity

import com.gcc.miti.group.entity.Group
import com.gcc.miti.user.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "last_read_chat_message")
class LastReadChatMessage(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    val group: Group,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    var chatMessage: ChatMessage
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}