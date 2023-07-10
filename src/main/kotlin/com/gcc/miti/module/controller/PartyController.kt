package com.gcc.miti.module.controller

import com.gcc.miti.module.entity.PartyList
import com.gcc.miti.module.service.PartyService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/party")
class PartyController(
    private val partyService: PartyService,
) {

    @PostMapping(path = ["/{id}"])
    fun makePartyList(@PathVariable id: String): PartyList {
        return partyService.makePartyList(id)
    }
}
