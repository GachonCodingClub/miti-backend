package com.gcc.miti.user.entity

import com.gcc.miti.chat.entity.ChatMessage
import com.gcc.miti.chat.entity.LastReadChatMessage
import com.gcc.miti.common.entity.BaseTimeEntity
import com.gcc.miti.group.entity.Group
import com.gcc.miti.group.entity.Party
import com.gcc.miti.group.entity.PartyMember
import com.gcc.miti.notification.entity.UserNotification
import com.gcc.miti.user.constants.Gender
import com.gcc.miti.user.constants.Height
import com.gcc.miti.user.constants.Weight
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false)
    var password: String,

    @Column(nullable = true)
    var description: String?,

    @Enumerated(value = EnumType.STRING)
    val gender: Gender,

    @Enumerated(value = EnumType.STRING)
    var height: Height,

    @Enumerated(value = EnumType.STRING)
    var weight: Weight,

    @Column(unique = true)
    var nickname: String,

    val birthDate: LocalDate,

) : BaseTimeEntity() {
    @Id
    var userId: String = ""
    fun toPartyMember(party: Party): PartyMember {
        return PartyMember().also {
            it.user = this
            it.party = party
        }
    }

    val age
        get() = LocalDate.now().year - birthDate.year

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var partyMembers: MutableList<PartyMember> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val chatMessages: MutableList<ChatMessage> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leader", cascade = [CascadeType.ALL], orphanRemoval = true)
    val groups: MutableList<Group> = mutableListOf()

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val lastReadChatMessages: MutableList<LastReadChatMessage> = mutableListOf()

    @OneToOne(fetch = FetchType.LAZY)
    val userNotification: UserNotification? = null
}
