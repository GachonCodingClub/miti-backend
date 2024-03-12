package com.gcc.miti.group.dto

import com.gcc.miti.user.dto.UserSummaryDto
import com.gcc.miti.group.entity.Party

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
                    partyMembers.map {
                        UserSummaryDto.toDto(it.user!!)
                    },
                )
            }
        }
    }
}
