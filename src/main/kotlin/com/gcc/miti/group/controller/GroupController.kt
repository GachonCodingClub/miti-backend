package com.gcc.miti.group.controller

import com.gcc.miti.group.dto.GroupPartiesDto
import com.gcc.miti.group.dto.GroupResponse
import com.gcc.miti.group.dto.CreateGroupReq
import com.gcc.miti.group.dto.UpdateGroupReq
import com.gcc.miti.auth.security.GetIdFromToken
import com.gcc.miti.group.dto.GroupListDto
import com.gcc.miti.group.service.GroupService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/groups")
class GroupController(
    private val groupService: GroupService,
) {

    @PostMapping("")
    @Operation(summary = "미팅방 만들기")
    fun createGroup(
        @RequestBody createGroupReq: CreateGroupReq,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): ResponseEntity<Boolean> {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(createGroupReq, userId))
    }

    @PatchMapping("/{groupId}")
    @Operation(summary = "미팅방 수정하기")
    fun updateGroup(
        @RequestBody updateGroupReq: UpdateGroupReq,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
        @PathVariable groupId: Long,
    ): Boolean {
        return groupService.updateGroup(updateGroupReq, userId, groupId)
    }

    @GetMapping("")
    @Operation(summary = "미팅방 확인하기 (페이지네이션)")
    fun getGroups(@PageableDefault pageable: Pageable): Page<GroupListDto> {
        return groupService.getGroups(pageable)
    }

    @GetMapping("/my")
    @Operation(summary = "내 미팅방 확인하기 (페이지네이션)")
    fun getMyGroups(
        @PageableDefault pageable: Pageable,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): Page<GroupListDto> {
        return groupService.getMyGroups(pageable, userId)
    }

    @Operation(summary = "신청한 파티 목록 조회")
    @GetMapping("/{groupId}/parties")
    fun getRequestedParties(
        @PathVariable groupId: Long,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): GroupPartiesDto {
        return groupService.getRequestedParties(groupId, userId)
    }

    @Operation(summary = "파티 수락하기")
    @GetMapping("/{groupId}/parties/{partyId}/accept")
    fun acceptParty(
        @PathVariable groupId: Long,
        @PathVariable partyId: Long,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): Boolean {
        return groupService.acceptParty(groupId, partyId, userId)
    }

    @Operation(summary = "파티 거절하기")
    @GetMapping("/{groupId}/parties/{partyId}/reject")
    fun rejectParty(
        @PathVariable groupId: Long,
        @PathVariable partyId: Long,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): Boolean {
        return groupService.rejectParty(groupId, partyId, userId)
    }

    @Operation(summary = "그룹 Id로 하나의 그룹 조회하기")
    @GetMapping("/{groupId}")
    fun getParty(@PathVariable groupId: Long): GroupResponse {
        return groupService.getGroup(groupId)
    }

    @Operation(summary = "미팅방 삭제하기 (방장용) 방폭파됨.")
    @DeleteMapping("/{groupId}")
    fun deleteGroup(
        @PathVariable groupId: Long,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): Boolean {
        return groupService.deleteGroup(groupId, userId)
    }

    @Operation(summary = "미팅방 나가기 (비방장용)")
    @GetMapping("/{groupId}/leave")
    fun leaveGroup(
        @PathVariable groupId: Long,
        @Parameter(hidden = true) @GetIdFromToken userId: String,
    ): Boolean {
        return groupService.leavePartyAndGroup(groupId, userId)
    }
}
