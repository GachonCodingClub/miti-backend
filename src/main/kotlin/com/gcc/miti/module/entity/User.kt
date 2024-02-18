package com.gcc.miti.module.entity

import com.gcc.miti.module.constants.Gender
import com.gcc.miti.module.constants.Height
import com.gcc.miti.module.constants.Weight
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false)
    var password: String,
    @Column(nullable = false)
    val userName: String,
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
}
