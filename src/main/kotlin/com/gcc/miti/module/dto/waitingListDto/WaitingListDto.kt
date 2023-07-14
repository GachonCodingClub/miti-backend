package com.gcc.miti.module.dto.waitingListDto

import com.gcc.miti.module.entity.Group
import com.gcc.miti.module.entity.Party
import com.gcc.miti.module.entity.WaitingList
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class WaitingListDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
) {
    companion object {
        fun toWaitingList(group: Group, party: Party): WaitingList {
            return WaitingList().also {
                it.group = group
                it.party = party
            }
        }
    }
}
