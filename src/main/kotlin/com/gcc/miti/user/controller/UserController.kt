package com.gcc.miti.user.controller

import com.gcc.miti.user.dto.ProfileResponse
import com.gcc.miti.user.dto.UpdateProfileRequest
import com.gcc.miti.auth.security.GetIdFromToken
import com.gcc.miti.user.dto.GetBlockedUsersResponse
import com.gcc.miti.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {
    @GetMapping("/me/profile")
    @Operation(summary = "내 프로필")
    fun getMyProfile(): ProfileResponse {
        return userService.getMyProfile()
    }

    @PatchMapping("/me/profile")
    @Operation(summary = "내 프로필 수정")
    fun updateProfile(@RequestBody updateProfileRequest: UpdateProfileRequest): Map<String, Boolean> {
        return mapOf("success" to userService.updateProfile(updateProfileRequest))
    }

    @DeleteMapping("/me")
    @Operation(summary = "회원탈퇴")
    fun deleteUser(@Parameter(hidden = true) @GetIdFromToken userId: String) {
        userService.deleteUser(userId)
    }

    @Operation(summary = "유저 차단")
    @PostMapping("/block")
    fun blockUser(@RequestParam("blockTargetNickname") blockTargetNickname: String, @Parameter(hidden = true) @GetIdFromToken userId: String) {
        userService.blockUser(blockTargetNickname, userId)
    }

    @Operation(summary = "유저 차단 해제")
    @PostMapping("/unblock")
    fun unblockUser(@RequestParam("blockTargetNickname") blockTargetNickname: String, @Parameter(hidden = true) @GetIdFromToken userId: String) {
        userService.unblockUser(blockTargetNickname, userId)
    }

    @Operation(summary = "차단 목록")
    @GetMapping("/me/blocked-users")
    fun getBlockedUsers(@Parameter(hidden = true) @GetIdFromToken userId: String): GetBlockedUsersResponse {
        return userService.getBlockedUsers(userId)
    }
}