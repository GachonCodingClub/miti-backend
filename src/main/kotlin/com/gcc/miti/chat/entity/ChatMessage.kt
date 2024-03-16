package com.gcc.miti.chat.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import com.gcc.miti.group.entity.Group
import com.gcc.miti.user.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "chat_message")
class ChatMessage(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(nullable = false)
    var content: String,

) : BaseTimeEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    var group: Group? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatMessage", cascade = [CascadeType.ALL], orphanRemoval = true)
    val lastReadChatMessages: MutableList<LastReadChatMessage> = mutableListOf()

    companion object {
        fun createGroupLeaveMessage(user: User, group: Group): ChatMessage {
            return ChatMessage(
                user,
                "[MITI]${user.nickname}님이 미팅에서 나가셨습니다.",
            ).also { it.group = group }
        }

        fun createGroupJoinMessage(user: User, group: Group): ChatMessage {
            return ChatMessage(
                user!!,
                "[MITI]${user.nickname}님이 미팅에 참가하셨습니다.",
            ).also { it.group = group }
        }
    }
}
