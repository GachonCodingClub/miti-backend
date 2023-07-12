package com.gcc.miti.module.controller

import com.gcc.miti.module.entity.Party
import com.gcc.miti.module.entity.PartyList
import com.gcc.miti.module.global.security.GetIdFromToken
import com.gcc.miti.module.service.PartyService
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

    @PostMapping("")
    fun makeParty(@RequestBody party: Party): Party? {
        return partyService.makeParty(party)
    }

    @PostMapping(path = ["/{partyId}"])
    fun makePartyList(@GetIdFromToken userId: String, @PathVariable("partyId") partyId: Long): PartyList? {
        return partyService.makePartyList(userId, partyId)
    }

//
//    @GetMapping(path = ["/{id}"])
//    fun findPartyList(@PathVariable id: Long): PartyList {
//        return partyService.showPartyList(id)
//    }
}
