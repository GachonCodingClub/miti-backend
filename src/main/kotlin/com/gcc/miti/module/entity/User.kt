package com.gcc.miti.module.entity

import com.gcc.miti.module.constants.Gender
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val userName: String,
    @Column(nullable = true)
    val description: String?,
    @Enumerated(value = EnumType.STRING)
    val gender: Gender,

    @Column(unique = true)
    val nickname: String,
) : BaseTimeEntity() {
    @Id
    var userId: String = ""
    fun toPartyMember(party: Party): PartyMember {
        return PartyMember().also {
            it.user = this
            it.party = party
        }
    }
}
