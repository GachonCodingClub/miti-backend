package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.Group
import com.gcc.miti.module.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long> {
    fun getByLeaderAndId(leader: User, id: Long): Group?
}
