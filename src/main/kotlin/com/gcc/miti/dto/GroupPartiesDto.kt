package com.gcc.miti.dto

import com.gcc.miti.entity.Party

data class GroupPartiesDto(
    val waitingParties: List<PartyMembersDto>,
    val acceptedParties: List<PartyMembersDto>,
    val leaderUserSummaryDto: UserSummaryDto,
)

class PartyMembersDto(
    val partyId: Long,
    val users: List<UserSummaryDto>,
) {
    companion object {
        fun partyToPartyMembersDto(party: Party): PartyMembersDto {
            with(party) {
                return PartyMembersDto(
                    id,
                    partyMember.map {
                        UserSummaryDto.toDto(it.user!!)
                    },
                )
            }
        }
    }
}
