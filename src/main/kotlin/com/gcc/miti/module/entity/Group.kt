package com.gcc.miti.module.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "my_group")
class Group(
    val description: String,
    val title: String,
    val maxUsers: Long,

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var meetDate: LocalDateTime? = null

    var meetPlace: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_user_id")
    var leader: User? = null

    @OneToMany(fetch = FetchType.LAZY)
    val parties: List<Party> = listOf()

    val acceptedParties: List<Party>
        get() {
            return parties.filter { it.isAccepted }
        }

    val waitingParties: List<Party>
        get() {
            return parties.filter { !it.isAccepted }
        }
}
