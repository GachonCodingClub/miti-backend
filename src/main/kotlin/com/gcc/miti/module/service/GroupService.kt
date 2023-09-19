package com.gcc.miti.module.service

import com.gcc.miti.module.dto.makegroupdto.GroupDto
import com.gcc.miti.module.repository.GroupRepository
import com.gcc.miti.module.repository.UserRepository
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
}
