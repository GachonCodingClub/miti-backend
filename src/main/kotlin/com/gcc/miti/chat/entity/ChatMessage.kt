package com.gcc.miti.chat.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import com.gcc.miti.group.entity.Group
import com.gcc.miti.user.entity.User
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "chat_message")
class ChatMessage(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(nullable = false)
    var content: String,

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),
) : BaseTimeEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    var group: Group? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
