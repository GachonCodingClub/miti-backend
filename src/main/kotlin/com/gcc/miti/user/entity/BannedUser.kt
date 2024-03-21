package com.gcc.miti.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "banned_users")
class BannedUser(
    @Column(name = "email", nullable = false, unique = true)
    val email: String,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}