package com.gcc.miti.group.dto

import com.gcc.miti.group.constants.GroupStatus
import com.gcc.miti.group.entity.Group
import com.gcc.miti.user.entity.User
import java.time.LocalDateTime

data class CreateGroupReq(
    val description: String,
    val title: String,
    val maxUsers: Int,
    val meetDate: LocalDateTime?,
    val meetPlace: String?,
    val nicknames: List<String>
) {
    companion object {
        fun toGroup(createGroupReq: CreateGroupReq, user: User): Group {
            return Group(
                createGroupReq.description,
                createGroupReq.title,
                createGroupReq.maxUsers,
                GroupStatus.OPEN,
            ).also {
                it.meetDate = createGroupReq.meetDate
                it.meetPlace = createGroupReq.meetPlace
                it.leader = user // 이부분 다시볼것
            }
        }
    }
}
