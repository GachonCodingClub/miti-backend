package com.gcc.miti.module.service

import com.gcc.miti.module.dto.UserDto
import com.gcc.miti.module.entity.PartyList
import com.gcc.miti.module.repository.PartyListRepository
import com.gcc.miti.module.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PartyService(
    private val partyListRepository: PartyListRepository,
    private val userRepository: UserRepository,

) {
    fun makePartyList(userid: String): PartyList {
        return partyListRepository.save(UserDto.toPartyList(userRepository.getReferenceById(userid)))
    }

    fun showPartyList(id: Long): PartyList {
        return partyListRepository.getReferenceById(id)
    }
}
