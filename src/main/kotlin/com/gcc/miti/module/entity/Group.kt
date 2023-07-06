package com.gcc.miti.module.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "my_group")
class Group(
    val description: String,
    val title: String,
    val maxUsers: Short,

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    val meetDate: LocalDateTime? = null

    val meetPlace: String? = null

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    val groupMembers: List<User> = listOf()

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    val groupAppliers: List<User> = listOf()

    @ManyToOne(fetch = FetchType.LAZY)
    val leader: User? = null
}
