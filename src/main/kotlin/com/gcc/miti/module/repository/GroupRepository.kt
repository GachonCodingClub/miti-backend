package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long>
