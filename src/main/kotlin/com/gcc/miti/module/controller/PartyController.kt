package com.gcc.miti.module.controller

import com.gcc.miti.module.entity.Party
import com.gcc.miti.module.global.security.GetIdFromToken
import com.gcc.miti.module.service.PartyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @PostMapping("/makeParty")
    fun makeParty(@RequestBody party: Party): Party? {
        return partyService.makeParty(party)
    }

    @PostMapping(path = ["/{partyId}"])
    fun makePartyList(@GetIdFromToken userId: String, @PathVariable("partyId") partyId: Long): ResponseEntity<Boolean> {
        return ResponseEntity.status(HttpStatus.CREATED).body(partyService.makePartyList(userId, partyId))
    }

//    @PostMapping(path = ["/{partyId}"])
//    fun addUserToParty(@PathVariable partyId: Long, @RequestBody partyDto: PartyDto): ResponseEntity<Boolean> {
//        return ResponseEntity.status(HttpStatus.CREATED).body(partyService.addUserToParty(partyId, partyDto,))
//    }

//    @GetMapping("/test")
//    fun test() { // print all party members
//        return partyService.test()
//    }
}
