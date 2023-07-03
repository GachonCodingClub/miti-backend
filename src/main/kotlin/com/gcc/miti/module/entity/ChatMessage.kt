package com.gcc.miti.module.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "chat_message")
class ChatMessage(

    @ManyToOne(fetch = FetchType.LAZY)
    var user: User,

    @Column(nullable = false)
    var content: String,

    @Column(nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
) : BaseTimeEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    val group: Group? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}
