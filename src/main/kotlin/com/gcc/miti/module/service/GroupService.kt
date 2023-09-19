package com.gcc.miti.module.service

import com.gcc.miti.module.dto.GroupPartiesDto
import com.gcc.miti.module.dto.PartyMembersDto
import com.gcc.miti.module.dto.makegroupdto.GroupDto
import com.gcc.miti.module.repository.GroupRepository
import com.gcc.miti.module.repository.PartyRepository
import com.gcc.miti.module.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val partyRepository: PartyRepository,
) {

    @Transactional
    fun makeGroup(groupDto: GroupDto, userId: String): Boolean {
        groupRepository.save(GroupDto.toGroup(groupDto, userRepository.getReferenceById(userId)))
        return true
    }

    @Transactional(readOnly = true)
    fun getRequestedParties(groupId: Long, userId: String): GroupPartiesDto {
        val group = groupRepository.getByLeaderAndId(userRepository.getReferenceById(userId), groupId)
        return GroupPartiesDto(
            group.waitingParties.map {
                PartyMembersDto.partyToPartyMembersDto(it)
            },
            group.acceptedParties.map {
                PartyMembersDto.partyToPartyMembersDto(it)
            },
        )
    }
}
