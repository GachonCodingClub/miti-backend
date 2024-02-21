package com.gcc.miti.repository

import com.gcc.miti.entity.PartyMember
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyMemberRepository : JpaRepository<PartyMember, Long>
