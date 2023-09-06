package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.PartyMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyListRepository : JpaRepository<PartyMember, Long>
