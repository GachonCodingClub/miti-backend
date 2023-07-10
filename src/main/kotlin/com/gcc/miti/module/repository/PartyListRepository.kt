package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.PartyList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PartyListRepository : JpaRepository<PartyList, Long>
