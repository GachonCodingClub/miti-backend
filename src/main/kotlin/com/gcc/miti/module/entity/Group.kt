package com.gcc.miti.module.entity

import com.gcc.miti.module.constants.GroupStatus
import com.gcc.miti.module.constants.PartyStatus
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Enumerated
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

    @Enumerated
    val groupStatus: GroupStatus,

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var meetDate: LocalDateTime? = null

    var meetPlace: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_user_id")
    var leader: User? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    val parties: List<Party> = listOf()

    val acceptedParties: List<Party>
        get() {
            return parties.filter { it.partyStatus == PartyStatus.ACCEPTED }
        }

    val waitingParties: List<Party>
        get() {
            return parties.filter { it.partyStatus == PartyStatus.WAITING }
        }

    fun acceptParty(partyId: Long) {
        val party = parties.find { it.id == partyId }!!
        val memberCount = party.partyMember.count()
        var sum = 1
        acceptedParties.forEach {
            sum += it.partyMember.count()
        }
        if (maxUsers - sum - memberCount >= 0) {
            party.partyStatus = PartyStatus.ACCEPTED
        }
    }

    fun rejectParty(partyId: Long) {
        val party = parties.find { it.id == partyId }!!
        party.partyStatus = PartyStatus.REJECTED
    }

    val countMembers: Int
        get() {
            var count = 0
            acceptedParties.forEach {
                count += it.partyMember.count()
            }
            return count + 1 // Plus Leader
        }
}
