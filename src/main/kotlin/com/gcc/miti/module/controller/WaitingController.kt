package com.gcc.miti.module.controller

import com.gcc.miti.module.service.WaitingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/waiting")
class WaitingController(
    private val waitingService: WaitingService,
) {
    @PostMapping(path = ["/{groupId}/{partyId}"]) // 그룹이랑 파티필요
    fun makeWaitingList(
        @PathVariable("groupId") groupId: Long,
        @PathVariable("partyId") partyId: Long,
    ): ResponseEntity<Boolean>? {
        return ResponseEntity.status(HttpStatus.CREATED).body(waitingService.makeWatingList(groupId, partyId))
    }
}
