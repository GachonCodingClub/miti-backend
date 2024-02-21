package com.gcc.miti.repository

import com.gcc.miti.entity.Party
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyRepository : JpaRepository<Party, Long>
