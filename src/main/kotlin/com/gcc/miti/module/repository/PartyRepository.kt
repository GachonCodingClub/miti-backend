package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.Party
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyRepository : JpaRepository<Party, Long>
