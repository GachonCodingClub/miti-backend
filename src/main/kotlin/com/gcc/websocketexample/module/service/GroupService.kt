package com.gcc.websocketexample.module.service

import com.gcc.websocketexample.module.dto.GroupMembersDto
import com.gcc.websocketexample.module.repository.GroupRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class GroupService(
    private val groupRepository: GroupRepository
) {
    @Transactional
    fun test(): List<GroupMembersDto> {
        return groupRepository.findById(1).get().groupMembers.map {
            GroupMembersDto.userToGroupMembersDto(it)
        }
    }

    fun makeGroup() {
    }
}
