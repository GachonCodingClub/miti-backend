package com.gcc.miti.module.service

import com.gcc.miti.module.repository.ParticipantRepository
import com.gcc.miti.module.repository.WaitingListRepository
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val participantRepository: ParticipantRepository,
    private val waitingListRepository: WaitingListRepository,
)
