package com.gcc.miti.group.repository

import com.gcc.miti.group.entity.PartyMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyMemberRepository : JpaRepository<PartyMember, Long>
