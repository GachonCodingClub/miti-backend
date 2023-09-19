package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.GroupPartiesDto
import com.gcc.miti.module.dto.makegroupdto.GroupDto
import com.gcc.miti.module.global.security.GetIdFromToken
import com.gcc.miti.module.service.GroupService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/group")
class GroupController(
    private val groupService: GroupService,
) {

    @PostMapping("")
    @Operation(summary = "미팅방 만들기")
    fun makeGroup(
        @RequestBody groupDto: GroupDto,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.makeGroup(groupDto, userId))
    }

    @Operation(summary = "신청한 파티 목록 조회")
    @GetMapping("/{groupId}/parties")
    fun getRequestedParties(
        @PathVariable groupId: Long,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): GroupPartiesDto {
        return groupService.getRequestedParties(groupId, userId)
    }
}
