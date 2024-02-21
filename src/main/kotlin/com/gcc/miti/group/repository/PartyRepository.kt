package com.gcc.miti.group.repository

import com.gcc.miti.group.entity.Party
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyRepository : JpaRepository<Party, Long>
