package com.gcc.miti.module.controller

import com.gcc.miti.module.dto.user.dto.ProfileRes
import com.gcc.miti.module.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {
    @GetMapping("/profile/my")
    @Operation(summary = "내 프로필")
    fun getMyProfile(): ProfileRes {
        return userService.getMyProfile()
    }
}