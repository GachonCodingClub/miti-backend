package com.gcc.miti.group.service

import com.gcc.miti.archive.repository.DeletedGroupRepository
import com.gcc.miti.group.constants.GroupStatus
import com.gcc.miti.group.constants.PartyStatus
import com.gcc.miti.group.dto.CreateGroupReq
import com.gcc.miti.group.dto.UpdateGroupReq
import com.gcc.miti.chat.entity.ChatMessage
import com.gcc.miti.chat.repository.ChatMessageRepository
import com.gcc.miti.archive.entity.DeletedGroup
import com.gcc.miti.chat.repository.LastReadChatMessageRepository
import com.gcc.miti.group.entity.Party
import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import com.gcc.miti.group.dto.GroupListDto
import com.gcc.miti.group.dto.GroupPartiesDto
import com.gcc.miti.group.dto.GroupRes
import com.gcc.miti.group.dto.PartyMembersDto
import com.gcc.miti.group.repository.GroupRepository
import com.gcc.miti.group.repository.PartyRepository
import com.gcc.miti.notification.service.NotificationService
import com.gcc.miti.user.dto.UserSummaryDto
import com.gcc.miti.user.repository.UserRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
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
    private val deletedGroupRepository: DeletedGroupRepository,
    private val lastReadChatMessageRepository: LastReadChatMessageRepository,
    private val notificationService: NotificationService
) {

    @Transactional
    fun createGroup(createGroupReq: CreateGroupReq, userId: String): Boolean {
        val leader = userRepository.getReferenceById(userId)
        val group =
            groupRepository.save(CreateGroupReq.toGroup(createGroupReq, leader))
        val users = userRepository.findAllByNicknameIn(createGroupReq.nicknames)
        val party = partyRepository.save(Party(PartyStatus.ACCEPTED).also { it.group = group })
        party.partyMember = users.map {
            it.toPartyMember(party)
        }.toMutableList()
        val chatMessages = mutableListOf<ChatMessage>()
        chatMessages.add(ChatMessage(leader, "[MITI]방장 ${leader.nickname} 님이 채팅방을 시작하였습니다.").also { it.group = group })
        chatMessages.add(ChatMessage(leader, "[MITI]미팅날짜가 3일 지난 미팅과 채팅방은 자동으로 삭제됩니다.").also { it.group = group })
        users.forEach {
            chatMessages.add(ChatMessage(
                it,
                "[MITI]${it.nickname}님이 미팅에 참가하셨습니다.",
            ).also { it.group = group })
        }
        chatMessageRepository.saveAll(chatMessages)
        return true
    }

    @Transactional
    @CacheEvict(cacheNames = ["group"], key = "#groupId")
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
        return groupRepository.findAllByMeetDateIsAfter(LocalDateTime.now(), pageable).map {
            GroupListDto.toDto(it)
        }
    }

    @Transactional(readOnly = true)
    fun getMyGroups(pageable: Pageable, userId: String): Page<GroupListDto> {
        val myGroups = groupRepository.findMyGroups(userId, pageable)

        val groupIds = myGroups.content.map { it.id }
        val lastReadChatMessages =
            lastReadChatMessageRepository.findAllByGroupIdsAndUser(groupIds, userRepository.getReferenceById(userId))

        return myGroups.map { group ->
            val lastReadChatMessage = lastReadChatMessages.find { it.group.id == group.id }?.let {
                chatMessageRepository.countByGroupAndIdGreaterThan(group, it.chatMessage.id)
            }
            GroupListDto.toDto(group, lastReadChatMessage, group.leader.userId == userId)
        }
    }

    @Transactional
    @CacheEvict(cacheNames = ["group"], key = "#groupId")
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
        notificationService.sendPartyAcceptedNotification(group, party)
        return true
    }

    @Transactional
    @CacheEvict(cacheNames = ["group"], key = "#groupId")
    fun rejectParty(groupId: Long, partyId: Long, userId: String): Boolean {
        val group =
            groupRepository.getByLeaderAndId(userRepository.getReferenceById(userId), groupId) ?: throw BaseException(
                BaseExceptionCode.NOT_FOUND,
            )
        group.rejectParty(partyId)
        return true
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#groupId", cacheNames = ["group"])
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
    @CacheEvict(cacheNames = ["group"], key = "#groupId")
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
    @CacheEvict(cacheNames = ["group"], key = "#groupId")
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
            if (group.groupStatus == GroupStatus.CLOSE) {
                group.groupStatus = GroupStatus.OPEN
            }
            return true
        }
        return false
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    @Transactional
    @CacheEvict(cacheNames = ["group"], allEntries = true)
    fun deleteGroupsAfterThreeDays() {
        val groups = groupRepository.findAllByMeetDateIsBefore(LocalDateTime.now().minusDays(3))
        val deletedGroups = groups.map { DeletedGroup.toDeletedGroup(it) }
        groupRepository.deleteAll(groups)
        deletedGroupRepository.saveAll(deletedGroups)
    }
}
