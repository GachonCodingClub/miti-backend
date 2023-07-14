package com.gcc.miti.module.service

import com.gcc.miti.module.dto.partyDto.PartyListDto
import com.gcc.miti.module.entity.Party
import com.gcc.miti.module.repository.GroupRepository
import com.gcc.miti.module.repository.PartyListRepository
import com.gcc.miti.module.repository.PartyRepository
import com.gcc.miti.module.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class PartyService(
    private val partyListRepository: PartyListRepository,
    private val userRepository: UserRepository,
    private val partyRepository: PartyRepository,
    private val groupRepository: GroupRepository,

) {
//    @Transactional
//    fun makePartyList(userId: String, partyId: Long): PartyList? {
//        val repoParty = partyRepository.findById(partyId)
//            .orElseThrow { throw EntityNotFoundException("Party not found") }
//        val repoUser = userRepository.getReferenceById(userId)
//        val repoGroup = groupRepository.findByLeader(repoUser) // 리더의 그룹찾기
//        if (repoGroup.maxUsers - 1 > repoGroup.userCount) {
//            return partyListRepository.save(
//                PartyListDto.toPartyList(
//                    repoUser,
//                    repoParty,
//                ),
//            )
//        } else {
//            throw BaseException(BaseExceptionCode.MAX_USER_ERROR)
//        }
//    }

    @Transactional
    fun makePartyList(userId: String, partyId: Long): Boolean {
        val repoParty = partyRepository.findById(partyId)
            .orElseThrow { throw EntityNotFoundException("Party not found") }
        val repoUser = userRepository.getReferenceById(userId)
        partyListRepository.save(
            PartyListDto.toPartyList(
                repoUser,
                repoParty,
            ),
        )
        return true
    }

    fun makeParty(party: Party): Party {
        return partyRepository.save(party)
    }

//
//    fun showPartyList(partyListId: Long): PartyList {
//        return partyListRepository.getReferenceById(partyListId)
//    }
}
