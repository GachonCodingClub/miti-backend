package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.GroupMembersDto
import com.gcc.miti.module.service.GroupService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/group")
class GroupController(
    private val groupService: GroupService
) {
    @GetMapping("/test")
    fun test(): List<GroupMembersDto> {
        return groupService.test()
    }

    @PostMapping("")
    fun makeGroup() {
    }
}
