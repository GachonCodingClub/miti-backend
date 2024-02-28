package com.gcc.miti.group.dto

import com.gcc.miti.group.entity.Party
import com.gcc.miti.group.entity.PartyMember
import com.gcc.miti.user.entity.User
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

data class PartyListDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
) {
    companion object {
        fun toPartyList(user: User, party: Party): PartyMember {
            return PartyMember().also {
                it.user = user
                it.party = party
            }
        }
    }
}
