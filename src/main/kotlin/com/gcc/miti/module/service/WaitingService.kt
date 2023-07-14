package com.gcc.miti.module.service

import com.gcc.miti.module.dto.waitingListDto.WaitingListDto
import com.gcc.miti.module.repository.GroupRepository
import com.gcc.miti.module.repository.PartyRepository
import com.gcc.miti.module.repository.UserRepository
import com.gcc.miti.module.repository.WaitingListRepository
import org.springframework.stereotype.Service

@Service
class WaitingService(
    val waitingListRepository: WaitingListRepository,
    val groupRepository: GroupRepository,
    val partyRepository: PartyRepository,
    val userRepository: UserRepository,

) {
    fun makeWatingList(groupId: Long, partyId: Long): Boolean {
        val repoGroup = groupRepository.getReferenceById(groupId)
        val repoParty = partyRepository.getReferenceById(partyId)
        waitingListRepository.save(
            WaitingListDto.toWaitingList(
                repoGroup,
                repoParty,
            ),
        )
        return true
    }

    fun admitRequest(waitingList: Long): Boolean {
        val repoWaitingList = waitingListRepository.getReferenceById(waitingList)
        repoWaitingList.flag = true
        return true
    }
}
