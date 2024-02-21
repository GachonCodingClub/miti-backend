package com.gcc.miti.dto.partydto

import com.gcc.miti.entity.Party
import com.gcc.miti.entity.PartyMember
import com.gcc.miti.entity.User
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

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
