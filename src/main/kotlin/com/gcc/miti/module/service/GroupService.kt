package com.gcc.miti.module.service

import com.gcc.miti.module.dto.GroupMembersDto
import com.gcc.miti.module.repository.GroupRepository
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
