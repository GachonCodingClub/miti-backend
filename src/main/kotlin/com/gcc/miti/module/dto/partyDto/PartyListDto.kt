package com.gcc.miti.module.dto.partyDto

import com.gcc.miti.module.entity.Party
import com.gcc.miti.module.entity.PartyList
import com.gcc.miti.module.entity.User
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class PartyListDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
) {
    companion object {
        fun toPartyList(user: User, party: Party): PartyList {
            return PartyList().also {
                it.user = user
                it.party = party
            }
        }
    }
}
