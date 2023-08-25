package com.gcc.miti.module.controller

import com.gcc.miti.module.global.security.GetIdFromToken
import com.gcc.miti.module.repository.WaitingListRepository
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
    private val waitingListRepository: WaitingListRepository,
) {
    @PostMapping(path = ["{groupId}/{partyId}"]) // 그룹이랑 파티필요
    fun makeWaitingList(
        @PathVariable("groupId") groupId: Long,
        @PathVariable("partyId") partyId: Long,
    ): ResponseEntity<Boolean>? {
        return ResponseEntity.status(HttpStatus.CREATED).body(waitingService.makeWaitingList(groupId, partyId))
    }

    @PostMapping("{waitingListId}")
    fun rejectWaitingList(@PathVariable waitingListId: Long, @GetIdFromToken userId: String): ResponseEntity<Boolean> {
        return ResponseEntity.status(HttpStatus.CREATED).body(waitingService.rejectRequest(waitingListId, userId))
    }

    @PostMapping("/admit/{waitingListId}")
    fun admitWaitingList(@PathVariable waitingListId: Long, @GetIdFromToken userId: String): ResponseEntity<Boolean> {
        return ResponseEntity.status(HttpStatus.CREATED).body(waitingService.admitRequest(waitingListId, userId))
    }
}
