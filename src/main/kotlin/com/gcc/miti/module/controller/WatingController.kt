package com.gcc.miti.module.controller

import com.gcc.miti.module.entity.Watinglist
import com.gcc.miti.module.service.WatingService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wating")
class WatingController(
    private val watingService: WatingService,
) {
    @PostMapping(path = ["/{groupId}/{partyId}"]) // 그룹이랑 파티필요
    fun makeWatingList(@PathVariable("groupId") groupId: Long, @PathVariable("partyId") partyId: Long): Watinglist? {
        return watingService.makeWatingList(groupId, partyId)
    }
}
