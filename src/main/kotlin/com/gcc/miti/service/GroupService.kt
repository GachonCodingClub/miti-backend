package com.gcc.miti.service

import com.gcc.miti.constants.GroupStatus
import com.gcc.miti.constants.PartyStatus
import com.gcc.miti.dto.*
import com.gcc.miti.dto.group.dto.CreateGroupReq
import com.gcc.miti.dto.group.dto.UpdateGroupReq
import com.gcc.miti.entity.ChatMessage
import com.gcc.miti.entity.DeletedGroup
import com.gcc.miti.entity.Party
import com.gcc.miti.global.exception.BaseException
import com.gcc.miti.global.exception.BaseExceptionCode
import com.gcc.miti.repository.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val partyRepository: PartyRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val deletedGroupRepository: DeletedGroupRepository
) {

    @Transactional
    fun createGroup(createGroupReq: CreateGroupReq, userId: String): Boolean {
        val group =
            groupRepository.save(CreateGroupReq.toGroup(createGroupReq, userRepository.getReferenceById(userId)))
        val users = userRepository.findAllByNicknameIn(createGroupReq.nicknames)
        val party = partyRepository.save(Party(PartyStatus.ACCEPTED).also { it.group = group })
        party.partyMember = users.map {
            it.toPartyMember(party)
        }.toMutableList()
        return true
    }

    @Transactional
    fun updateGroup(updateGroupReq: UpdateGroupReq, userId: String, groupId: Long): Boolean {
        val group = groupRepository.getReferenceById(groupId)
        if (group.leader.userId != userId) {
            throw BaseException(BaseExceptionCode.BAD_REQUEST)
        }
        group.title = updateGroupReq.title
        group.description = updateGroupReq.description
        updateGroupReq.meetDate?.let {
            group.meetDate = it
        }
        updateGroupReq.meetPlace?.let {
            group.meetPlace = it
        }
        return true
    }

    @Transactional(readOnly = true)
    fun getRequestedParties(groupId: Long, userId: String): GroupPartiesDto {
        val group = groupRepository.findByIdOrNull(groupId) ?: throw BaseException(BaseExceptionCode.NOT_FOUND)
        var waitingParties = emptyList<PartyMembersDto>()
        if (group.leader.userId == userId) {
            waitingParties = group.waitingParties.map {
                PartyMembersDto.partyToPartyMembersDto(it)
            }
        }
        return GroupPartiesDto(
            waitingParties,
            group.acceptedParties.map {
                PartyMembersDto.partyToPartyMembersDto(it)
            },
            UserSummaryDto.toDto(group.leader),
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
        val party = group.parties.find { it.id == partyId } ?: throw BaseException(BaseExceptionCode.NOT_FOUND)
        party.partyMember.forEach {
            chatMessageRepository.save(
                ChatMessage(
                    it.user!!,
                    "[MITI]${it.user!!.nickname}님이 미팅에 참가하셨습니다.",
                ).also { it.group = group },
            )
        }
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
            leaderUserSummaryDto = UserSummaryDto.toDto(group.leader),
            groupStatus = group.groupStatus,
        )
    }

    @Transactional
    fun deleteGroup(groupId: Long, userId: String): Boolean {
        val group =
            groupRepository.getByLeaderAndId(userRepository.getReferenceById(userId), groupId) ?: throw BaseException(
                BaseExceptionCode.NOT_FOUND,
            )
        groupRepository.delete(group)
        deletedGroupRepository.save(DeletedGroup.toDeletedGroup(group))
        return true
    }

    @Transactional
    fun leaveGroup(groupId: Long, userId: String): Boolean {
        val user = userRepository.getReferenceById(userId)
        val group = groupRepository.findByIdOrNull(groupId) ?: throw BaseException(BaseExceptionCode.NOT_FOUND)
        chatMessageRepository.save(
            ChatMessage(
                user,
                "[MITI]${user.nickname}님이 미팅에서 나가셨습니다.",
            ).also { it.group = group },
        )
        group.parties.flatMap { it.partyMember }.find { it.user?.userId == userId }?.let { partyMember ->
            group.parties.find { it.id == partyMember.party?.id }?.partyMember?.remove(partyMember)
            if(group.groupStatus == GroupStatus.CLOSE){
                group.groupStatus = GroupStatus.OPEN
            }
            return true
        }
        return false
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    @Transactional
    fun deleteGroupsAfterThreeDays(){
        val groups = groupRepository.findAllByMeetDateIsBefore(LocalDateTime.now().minusDays(3))
        val deletedGroups = groups.map { DeletedGroup.toDeletedGroup(it) }
        groupRepository.deleteAll(groups)
        deletedGroupRepository.saveAll(deletedGroups)
    }
}
