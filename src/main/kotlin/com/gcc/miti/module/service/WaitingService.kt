package com.gcc.miti.module.service

import com.gcc.miti.module.dto.waitingListDto.WaitingListDto
import com.gcc.miti.module.repository.GroupRepository
import com.gcc.miti.module.repository.PartyRepository
import com.gcc.miti.module.repository.UserRepository
import com.gcc.miti.module.repository.WaitingListRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WaitingService(
    val waitingListRepository: WaitingListRepository,
    val groupRepository: GroupRepository,
    val partyRepository: PartyRepository,
    val userRepository: UserRepository,

) {
    @Transactional
    fun makeWaitingList(groupId: Long, partyId: Long): Boolean {
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

    @Transactional
    fun admitRequest(waitingListId: Long): Boolean {
        val repoWaitingList = waitingListRepository.getReferenceById(waitingListId)
        repoWaitingList.flag = true
        return true
    }

    @Transactional
    fun rejectRequest(waitingListId: Long): Boolean {
        val repoWaitingList = waitingListRepository.getReferenceById(waitingListId)
        repoWaitingList.flag = false
        return true
    }
}
