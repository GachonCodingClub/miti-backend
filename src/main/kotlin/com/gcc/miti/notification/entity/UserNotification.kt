package com.gcc.miti.notification.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import com.gcc.miti.user.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "user_notification")
class UserNotification(
    @Id
    val id: String,

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(name = "is_agreed")
    val isAgreed: Boolean,

    @Column(name = "token")
    val token: String
): BaseTimeEntity()
