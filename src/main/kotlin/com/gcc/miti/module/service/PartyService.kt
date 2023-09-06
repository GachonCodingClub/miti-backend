package com.gcc.miti.module.service

import com.gcc.miti.module.dto.partydto.PartyDto
import com.gcc.miti.module.dto.partydto.PartyListDto
import com.gcc.miti.module.entity.Party
import com.gcc.miti.module.global.exception.BaseException
import com.gcc.miti.module.global.exception.BaseExceptionCode
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
    @Transactional
    fun makePartyList(userId: String, partyId: Long): Boolean {
        val party = partyRepository.findById(partyId)
            .orElseThrow { throw EntityNotFoundException("Party not found") }
        val user = userRepository.getReferenceById(userId)
        partyListRepository.save(
            PartyListDto.toPartyList(
                user,
                party,
            ),
        )
        return true
    }

    @Transactional
    fun makeParty(partyDto: PartyDto, userId: String, groupId: Long): Boolean {
        val group = groupRepository.getReferenceById(groupId)
        if (group.leader!!.userId == userId) {
            throw BaseException(BaseExceptionCode.BAD_REQUEST)
        }
        val users = userRepository.findAllByNicknameIn(partyDto.nicknames).toMutableList()
        val party = partyRepository.save(Party().also { it.group = group })
        users.add(userRepository.getReferenceById(userId))
        party.partyMember = users.map {
            it.toPartyMember(party)
        }.toMutableList()
        return true
    }
}
