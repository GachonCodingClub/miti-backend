package com.gcc.miti.module.service

import com.gcc.miti.module.constants.PartyStatus
import com.gcc.miti.module.dto.GroupListDto
import com.gcc.miti.module.dto.GroupPartiesDto
import com.gcc.miti.module.dto.GroupRes
import com.gcc.miti.module.dto.PartyMembersDto
import com.gcc.miti.module.dto.group.dto.CreateGroupReq
import com.gcc.miti.module.entity.Party
import com.gcc.miti.module.global.exception.BaseException
import com.gcc.miti.module.global.exception.BaseExceptionCode
import com.gcc.miti.module.repository.GroupRepository
import com.gcc.miti.module.repository.PartyRepository
import com.gcc.miti.module.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository, private val partyRepository: PartyRepository,
) {

    @Transactional
    fun makeGroup(createGroupReq: CreateGroupReq, userId: String): Boolean {
        val group = groupRepository.save(CreateGroupReq.toGroup(createGroupReq, userRepository.getReferenceById(userId)))
        val users = userRepository.findAllByNicknameIn(createGroupReq.nicknames)
        val party = partyRepository.save(Party(PartyStatus.ACCEPTED).also { it.group = group })
        party.partyMember = users.map {
            it.toPartyMember(party)
        }.toMutableList()
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

    @Transactional(readOnly = true)
    fun getGroups(pageable: Pageable): Page<GroupListDto> {
        return groupRepository.findAll(pageable).map {
            GroupListDto.toDto(it)
        }
    }

    @Transactional(readOnly = true)
    fun getMyGroups(pageable: Pageable, userId: String): Page<GroupListDto> {
        return groupRepository.findMyGroups(userId, pageable).map {
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

    @Transactional
    fun getGroup(groupId: Long): GroupRes {
        val group = groupRepository.getReferenceById(groupId)
        return GroupRes(
            description = group.description,
            title = group.title,
            maxUsers = group.maxUsers,
            meetDate = group.meetDate,
            meetPlace = group.meetPlace,
        ).also {
            group.acceptedParties
        }
    }
}
