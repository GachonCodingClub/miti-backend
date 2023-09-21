package com.gcc.miti.module.dto

import com.gcc.miti.module.entity.Group
import java.time.LocalDateTime

class GroupListDto(
    val meetDate: LocalDateTime?,
    val meetPlace: String?,
    val maxUsers: Int,
    val nowUsers: Int,
    val title: String,
    val description: String,
) {
    companion object {
        fun toDto(group: Group): GroupListDto {
            with(group) {
                return GroupListDto(
                    meetDate,
                    meetPlace,
                    maxUsers,
                    countMembers,
                    title,
                    description,
                )
            }
        }
    }
}