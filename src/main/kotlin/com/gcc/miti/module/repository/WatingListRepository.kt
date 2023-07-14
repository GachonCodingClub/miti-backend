package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.Watinglist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WatingListRepository : JpaRepository<Watinglist, Long> {
    fun findByGroupId(partyId: Long): Long
}
