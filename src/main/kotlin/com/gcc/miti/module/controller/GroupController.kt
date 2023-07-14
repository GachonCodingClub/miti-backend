package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.makeGroupDto.GroupDto
import com.gcc.miti.module.global.security.GetIdFromToken
import com.gcc.miti.module.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/group")
class GroupController(
    private val groupService: GroupService,
) {
//    @GetMapping("/test")
//    fun test(): List<GroupMembersDto> {
//        return groupService.test()
//    }

    @PostMapping("")
    fun makeGroup(@RequestBody groupDto: GroupDto, @GetIdFromToken userId: String): ResponseEntity<Boolean> {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.makeGroup(groupDto, userId))
    }
}
