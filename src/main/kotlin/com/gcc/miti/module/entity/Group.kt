package com.gcc.miti.module.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "my_group")
class Group(
    val description: String,

    val maxUsers: Short

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    val meetDate: LocalDateTime? = null

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    val groupMembers: List<User> = listOf()

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    val groupAppliers: List<User> = listOf()

    @ManyToOne(fetch = FetchType.LAZY)
    val leader: User? = null
}
