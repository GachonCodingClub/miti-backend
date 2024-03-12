package com.gcc.miti.group.controller

import com.gcc.miti.group.dto.PartyDto
import com.gcc.miti.auth.security.GetIdFromToken
import com.gcc.miti.group.service.PartyService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/party")
class PartyController(
    private val partyService: PartyService,
) {

    @Operation(summary = "{groupId}(미팅방)에 1~N명 파티(참가) 신청하기")
    @PostMapping("/{groupId}")
    fun makeParty(
        @PathVariable(name = "groupId") groupId: Long,
        @RequestBody partyDto: PartyDto,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): Boolean {
        return partyService.makeParty(partyDto, userId, groupId)
    }
}
