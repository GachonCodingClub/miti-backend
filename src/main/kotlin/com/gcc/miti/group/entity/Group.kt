package com.gcc.miti.group.entity

import com.gcc.miti.chat.entity.ChatMessage
import com.gcc.miti.chat.entity.LastReadChatMessage
import com.gcc.miti.common.entity.BaseTimeEntity
import com.gcc.miti.group.constants.GroupStatus
import com.gcc.miti.group.constants.PartyStatus
import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import com.gcc.miti.user.entity.User
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "my_group")
class Group(
    @Column(name = "description")
    var description: String,

    @Column(name = "title")
    var title: String,

    @Column(name = "max_users")
    val maxUsers: Int,

    @Column(name = "group_status")
    @Enumerated(EnumType.STRING)
    var groupStatus: GroupStatus,

    ) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(name = "meet_date")
    var meetDate: LocalDateTime? = null

    @Column(name = "meet_place")
    var meetPlace: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_user_id")
    lateinit var leader: User

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = [CascadeType.ALL])
    var parties: MutableList<Party> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = [CascadeType.ALL])
    var chatMessages: MutableList<ChatMessage> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = [CascadeType.ALL])
    var lastReadChatMessages: MutableList<LastReadChatMessage> = mutableListOf()

    val acceptedParties: List<Party>
        get() {
            return parties.filter { it.partyStatus == PartyStatus.ACCEPTED }
        }

    val acceptedPartyMembers: List<PartyMember>
        get() {
            return acceptedParties.flatMap { it.partyMembers }
        }

    val acceptedUsers: List<User>
        get() = acceptedParties.flatMap { it.partyMembers }.mapNotNull { it.user }

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
        val newMemberCount = party.partyMembers.count()
        var acceptedPartyMemberCount = 1 // Count Leader
        acceptedParties.forEach {
            acceptedPartyMemberCount += it.partyMembers.count()
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

    fun isGroupMember(userId: String): Boolean {
        return this.leader.userId == userId || acceptedParties.flatMap { it.partyMembers }
            .any { it.user?.userId == userId }
    }

    val countMembers: Int
        get() {
            var count = 0
            acceptedParties.forEach {
                count += it.partyMembers.count()
            }
            return count + 1 // Plus Leader
        }
}
