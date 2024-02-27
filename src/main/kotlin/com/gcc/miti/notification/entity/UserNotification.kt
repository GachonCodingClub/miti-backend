package com.gcc.miti.notification.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import com.gcc.miti.user.entity.User
import javax.persistence.*

@Entity
@Table(name = "user_notification")
class UserNotification(
    @Id
    val id: String,

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @Column(name = "is_agreed")
    val isAgreed: Boolean,

    @Column(name = "token")
    val token: String
): BaseTimeEntity()
