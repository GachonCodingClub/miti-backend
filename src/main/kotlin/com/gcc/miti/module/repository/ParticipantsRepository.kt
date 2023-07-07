package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.Participants
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParticipantsRepository : JpaRepository<Participants, Long>
