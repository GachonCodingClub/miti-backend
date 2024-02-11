package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.user.dto.ProfileRes
import com.gcc.miti.module.dto.user.dto.UpdateProfileReq
import com.gcc.miti.module.global.security.GetIdFromToken
import com.gcc.miti.module.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @GetMapping("/profile/my")
    @Operation(summary = "내 프로필")
    fun getMyProfile(): ProfileRes {
        return userService.getMyProfile()
    }

    @PatchMapping("/profile/my")
    @Operation(summary = "내 프로필 수정")
    fun updateProfile(@RequestBody updateProfileReq: UpdateProfileReq) {
        userService.updateProfile(updateProfileReq)
    }

    @DeleteMapping("/me")
    @Operation(summary = "회원탈퇴")
    fun deleteUser(@Parameter(hidden = true) @GetIdFromToken userId: String) {
        userService.deleteUser(userId)
    }
}