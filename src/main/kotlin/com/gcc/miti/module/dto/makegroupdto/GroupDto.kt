package com.gcc.miti.module.dto.makegroupdto

import com.gcc.miti.module.constants.GroupStatus
import com.gcc.miti.module.entity.Group
import com.gcc.miti.module.entity.User
import java.time.LocalDateTime

data class GroupDto(
    val description: String,
    val title: String,
    val maxUsers: Int,
    val meetDate: LocalDateTime?,
    val meetPlace: String?,
) {
    companion object {
        fun toGroup(groupDto: GroupDto, user: User): Group {
            return Group(
                groupDto.description,
                groupDto.title,
                groupDto.maxUsers,
                GroupStatus.OPEN,
            ).also {
                it.meetDate = groupDto.meetDate
                it.meetPlace = groupDto.meetPlace
                it.leader = user // 이부분 다시볼것
            }
        }
    }
}
