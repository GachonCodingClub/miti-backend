package com.gcc.miti.module.service

import com.gcc.miti.module.dto.makeGroupDto.GroupDto
import com.gcc.miti.module.entity.Group
import com.gcc.miti.module.repository.GroupRepository
import com.gcc.miti.module.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GroupService(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
) {
//    @Transactional
//    fun test(): List<GroupMembersDto> {
//        return groupRepository.findById(1).get().groupMembers.map {
//            GroupMembersDto.userToGroupMembersDto(it)
//        }
//    }

    @Transactional
    fun makeGroup(groupDto: GroupDto, userId: String): Group {
        return groupRepository.save(GroupDto.toGroup(groupDto, userRepository.getReferenceById(userId)))
    }
}
