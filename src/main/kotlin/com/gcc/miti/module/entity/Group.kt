package com.gcc.miti.module.entity

import com.gcc.miti.module.constants.GroupStatus
import com.gcc.miti.module.constants.PartyStatus
import com.gcc.miti.module.global.exception.BaseException
import com.gcc.miti.module.global.exception.BaseExceptionCode
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "my_group")
class Group(
    val description: String,
    val title: String,
    val maxUsers: Int,

    @Enumerated(EnumType.STRING)
    var groupStatus: GroupStatus,

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var meetDate: LocalDateTime? = null

    var meetPlace: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_user_id")
    lateinit var leader: User

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = [CascadeType.ALL])
    var parties: MutableList<Party> = mutableListOf()

    val acceptedParties: List<Party>
        get() {
            return parties.filter { it.partyStatus == PartyStatus.ACCEPTED }
        }

    val waitingParties: List<Party>
        get() {
            return parties.filter { it.partyStatus == PartyStatus.WAITING }
        }

    fun acceptParty(partyId: Long) {
        if (groupStatus == GroupStatus.CLOSE) {
            throw BaseException(BaseExceptionCode.BAD_REQUEST)
        }
        val party = parties.find { it.id == partyId } ?: throw BaseException(
            BaseExceptionCode.NOT_FOUND,
        )
        val newMemberCount = party.partyMember.count()
        var acceptedPartyMemberCount = 1 // Count Leader
        acceptedParties.forEach {
            acceptedPartyMemberCount += it.partyMember.count()
        }
        if (maxUsers - acceptedPartyMemberCount - newMemberCount > 0) {
            party.partyStatus = PartyStatus.ACCEPTED
        } else if (maxUsers - acceptedPartyMemberCount - newMemberCount == 0) { // 인원 모집 종료
            party.partyStatus = PartyStatus.ACCEPTED
            groupStatus = GroupStatus.CLOSE
        } else {
            throw BaseException(BaseExceptionCode.MAX_USER_ERROR)
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
