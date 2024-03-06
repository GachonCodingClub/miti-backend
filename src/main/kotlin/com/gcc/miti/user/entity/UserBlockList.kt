package com.gcc.miti.user.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_block_list")
class UserBlockList(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_target_user_id")
    val blockedTargetUser: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}