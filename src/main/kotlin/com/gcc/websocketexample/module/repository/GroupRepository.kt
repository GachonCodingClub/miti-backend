package com.gcc.websocketexample.module.repository

import com.gcc.websocketexample.module.entity.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Long>
