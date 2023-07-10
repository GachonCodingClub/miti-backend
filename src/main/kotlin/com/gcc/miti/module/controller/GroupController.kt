package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.makeGroup.GroupDto
import com.gcc.miti.module.entity.Group
import com.gcc.miti.module.global.security.GetIdFromToken
import com.gcc.miti.module.service.GroupService
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
    fun makeGroup(@RequestBody groupDto: GroupDto, @GetIdFromToken id: String): Group? {
        return groupService.makeGroup(groupDto, id)
    }
}
