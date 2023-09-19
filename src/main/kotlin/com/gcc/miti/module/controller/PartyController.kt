package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.partydto.PartyDto
import com.gcc.miti.module.global.security.GetIdFromToken
import com.gcc.miti.module.service.PartyService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/party")
class PartyController(
    private val partyService: PartyService,
) {

    @Operation(summary = "{groupId}(미팅방)에 1~N명 파티(참가) 신청하기")
    @PostMapping("/{groupId}")
    fun makeParty(
        @PathVariable(name = "groupId") groupId: Long,
        @RequestBody partyDto: PartyDto,
        @GetIdFromToken userId: String,
    ): Boolean {
        return partyService.makeParty(partyDto, userId, groupId)
    }

//    @PostMapping(path = ["/{partyId}"])
//    fun makePartyList(@GetIdFromToken userId: String, @PathVariable("partyId") partyId: Long): ResponseEntity<Boolean> {
//        return ResponseEntity.status(HttpStatus.CREATED).body(partyService.makePartyList(userId, partyId))
//    }

//    @PostMapping(path = ["/{partyId}"])
//    fun addUserToParty(@PathVariable partyId: Long, @RequestBody partyDto: PartyDto): ResponseEntity<Boolean> {
//        return ResponseEntity.status(HttpStatus.CREATED).body(partyService.addUserToParty(partyId, partyDto,))
//    }

//    @GetMapping("/test")
//    fun test() { // print all party members
//        return partyService.test()
//    }
}
