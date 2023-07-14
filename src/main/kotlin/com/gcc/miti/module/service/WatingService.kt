package com.gcc.miti.module.service

import com.gcc.miti.module.dto.watingListDto.WatingListDto
import com.gcc.miti.module.entity.Watinglist
import com.gcc.miti.module.repository.GroupRepository
import com.gcc.miti.module.repository.PartyRepository
import com.gcc.miti.module.repository.UserRepository
import com.gcc.miti.module.repository.WatingListRepository
import org.springframework.stereotype.Service

@Service
class WatingService(
    val watingListRepository: WatingListRepository,
    val groupRepository: GroupRepository,
    val partyRepository: PartyRepository,
    val userRepository: UserRepository,

) {
    fun makeWatingList(groupId: Long, partyId: Long): Watinglist {
        val repoGroup = groupRepository.getReferenceById(groupId)
        val repoParty = partyRepository.getReferenceById(partyId)
        return watingListRepository.save(
            WatingListDto.toWatingList(
                repoGroup,
                repoParty,
            ),
        )
    }
}
