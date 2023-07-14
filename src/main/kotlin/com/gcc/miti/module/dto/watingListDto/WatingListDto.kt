package com.gcc.miti.module.dto.watingListDto

import com.gcc.miti.module.entity.Group
import com.gcc.miti.module.entity.Party
import com.gcc.miti.module.entity.Watinglist
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

data class WatingListDto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val watinglistId: Long,
) {
    companion object {
        fun toWatingList(group: Group, party: Party): Watinglist {
            return Watinglist().also {
                it.group = group
                it.party = party
            }
        }
    }
}
