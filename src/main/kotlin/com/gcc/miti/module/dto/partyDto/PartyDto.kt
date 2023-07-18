package com.gcc.miti.module.dto.partyDto

import com.gcc.miti.module.entity.Party
import com.gcc.miti.module.entity.User

data class PartyDto(
    val roomTitle: String,
) {
    companion object {
        fun toParty(partyDto: PartyDto, user: User): Party {
            return Party(
                partyDto.roomTitle,
            ).also {
                it.participants = listOf(user.userId)
            }
        }
    }
}
