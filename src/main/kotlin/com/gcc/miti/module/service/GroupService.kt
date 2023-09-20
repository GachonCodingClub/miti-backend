package com.gcc.miti.module.service

import com.gcc.miti.module.dto.GroupListDto
import com.gcc.miti.module.dto.GroupPartiesDto
import com.gcc.miti.module.dto.PartyMembersDto
import com.gcc.miti.module.dto.makegroupdto.GroupDto
import com.gcc.miti.module.global.exception.BaseException
import com.gcc.miti.module.global.exception.BaseExceptionCode
import com.gcc.miti.module.repository.GroupRepository
import com.gcc.miti.module.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun makeGroup(groupDto: GroupDto, userId: String): Boolean {
        groupRepository.save(GroupDto.toGroup(groupDto, userRepository.getReferenceById(userId)))
        return true
    }

    @Transactional(readOnly = true)
    fun getRequestedParties(groupId: Long, userId: String): GroupPartiesDto {
        val group =
            groupRepository.getByLeaderAndId(userRepository.getReferenceById(userId), groupId) ?: throw BaseException(
                BaseExceptionCode.NOT_FOUND,
            )
        return GroupPartiesDto(
            group.waitingParties.map {
                PartyMembersDto.partyToPartyMembersDto(it)
            },
            group.acceptedParties.map {
                PartyMembersDto.partyToPartyMembersDto(it)
            },
        )
    }

    @Transactional
    fun getGroups(pageable: Pageable): Page<GroupListDto> {
        return groupRepository.findAll(pageable).map {
            GroupListDto.toDto(it)
        }
    }

    @Transactional
    fun acceptParty(groupId: Long, partyId: Long, userId: String): Boolean {
        val group =
            groupRepository.getByLeaderAndId(userRepository.getReferenceById(userId), groupId) ?: throw BaseException(
                BaseExceptionCode.NOT_FOUND,
            )
        group.acceptParty(partyId)
        return true
    }

    @Transactional
    fun rejectParty(groupId: Long, partyId: Long, userId: String): Boolean {
        val group =
            groupRepository.getByLeaderAndId(userRepository.getReferenceById(userId), groupId) ?: throw BaseException(
                BaseExceptionCode.NOT_FOUND,
            )
        group.rejectParty(partyId)
        return true
    }
}
