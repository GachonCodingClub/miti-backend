package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.WaitingList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WaitingListRepository : JpaRepository<WaitingList, Long> {
    fun findByPartyId(partyId: Long)
}
