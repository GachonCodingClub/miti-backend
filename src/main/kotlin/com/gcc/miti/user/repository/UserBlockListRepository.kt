package com.gcc.miti.user.repository

import com.gcc.miti.user.entity.User
import com.gcc.miti.user.entity.UserBlockList
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserBlockListRepository : JpaRepository<UserBlockList, Long> {
    fun deleteByBlockedTargetUserAndUser(blockedTargetUser: User, user: User): Int

    @EntityGraph(attributePaths = ["blockedTargetUser"])
    fun findAllByUser(user: User): List<UserBlockList>
}