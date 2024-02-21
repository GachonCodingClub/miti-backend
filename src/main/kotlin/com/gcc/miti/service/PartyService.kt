package com.gcc.miti.service

import com.gcc.miti.dto.partydto.PartyDto
import com.gcc.miti.entity.Party
import com.gcc.miti.global.exception.BaseException
import com.gcc.miti.global.exception.BaseExceptionCode
import com.gcc.miti.repository.GroupRepository
import com.gcc.miti.repository.PartyMemberRepository
import com.gcc.miti.repository.PartyRepository
import com.gcc.miti.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PartyService(
    private val partyMemberRepository: PartyMemberRepository,
    private val userRepository: UserRepository,
    private val partyRepository: PartyRepository,
    private val groupRepository: GroupRepository,

    ) {
    @Transactional
    fun makeParty(partyDto: PartyDto, userId: String, groupId: Long): Boolean {
        val group = groupRepository.getReferenceById(groupId)
        val user = userRepository.getReferenceById(userId)
        if (group.leader.userId == userId) {
            throw BaseException(BaseExceptionCode.BAD_REQUEST)
        }
        if (partyDto.nicknames.find { it == user.nickname } != null) throw BaseException(BaseExceptionCode.BAD_REQUEST)
        if(group.parties.map { it.partyMember }.flatten().find { it.user?.userId == user.userId } != null) throw BaseException(BaseExceptionCode.BAD_REQUEST)
        val users = userRepository.findAllByNicknameIn(partyDto.nicknames).toMutableList()
        val party = partyRepository.save(Party().also { it.group = group })
        users.add(userRepository.getReferenceById(userId))
        party.partyMember = users.map {
            it.toPartyMember(party)
        }.toMutableList()
        return true
    }
}
