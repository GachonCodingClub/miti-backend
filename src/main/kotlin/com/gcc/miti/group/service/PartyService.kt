package com.gcc.miti.group.service

import com.gcc.miti.group.dto.PartyDto
import com.gcc.miti.group.entity.Party
import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import com.gcc.miti.group.repository.GroupRepository
import com.gcc.miti.group.repository.PartyMemberRepository
import com.gcc.miti.group.repository.PartyRepository
import com.gcc.miti.notification.service.NotificationService
import com.gcc.miti.user.repository.UserRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PartyService(
    private val partyMemberRepository: PartyMemberRepository,
    private val userRepository: UserRepository,
    private val partyRepository: PartyRepository,
    private val groupRepository: GroupRepository,
    private val notificationService: NotificationService,

    ) {
    @CacheEvict(cacheNames = ["group"], key = "#groupId")
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
        notificationService.sendNewPartyRequestNotification(group.leader.userId, group)
        return true
    }
}
