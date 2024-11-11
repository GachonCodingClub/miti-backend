package com.gcc.miti.group.service

import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import com.gcc.miti.group.dto.PartyDto
import com.gcc.miti.group.entity.Group
import com.gcc.miti.group.entity.Party
import com.gcc.miti.group.repository.GroupRepository
import com.gcc.miti.group.repository.PartyRepository
import com.gcc.miti.notification.service.NotificationService
import com.gcc.miti.user.entity.User
import com.gcc.miti.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PartyService(
    private val userRepository: UserRepository,
    private val partyRepository: PartyRepository,
    private val groupRepository: GroupRepository,
    private val notificationService: NotificationService,

    ) {
    @Transactional
    fun makeParty(partyDto: PartyDto, userId: String, groupId: Long): Boolean {
        val group = groupRepository.getReferenceById(groupId)
        val user = userRepository.getReferenceById(userId)
        checkMakePartyPreconditionsOrThrow(partyDto, user, group)
        val users = userRepository.findAllByNicknameIn(partyDto.nicknames) + user
        val party = partyRepository.save(Party().also { it.group = group })
        party.partyMembers.addAll(users.map { it.toPartyMember(party) })
        notificationService.sendNewPartyRequestNotification(group.leader.userId, group)
        return true
    }

    private fun checkMakePartyPreconditionsOrThrow(partyDto: PartyDto, user: User, group: Group) {
        if (group.leader.userId == user.userId) {
            throw BaseException(BaseExceptionCode.BAD_REQUEST)
        }
        if (partyDto.nicknames.any { it == user.nickname }) throw BaseException(BaseExceptionCode.BAD_REQUEST)
        if (group.parties.map { it.partyMembers }.flatten()
                .find { it.user?.userId == user.userId } != null
        ) {
            throw BaseException(BaseExceptionCode.BAD_REQUEST)
        }
    }
}
