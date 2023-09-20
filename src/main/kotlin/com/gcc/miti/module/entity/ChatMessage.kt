package com.gcc.miti.module.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "chat_message")
class ChatMessage(

    @ManyToOne(fetch = FetchType.LAZY)
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
