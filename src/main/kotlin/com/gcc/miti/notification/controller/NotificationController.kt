package com.gcc.miti.notification.controller

import com.gcc.miti.auth.security.GetIdFromToken
import com.gcc.miti.notification.dto.NotificationTestRequest
import com.gcc.miti.notification.dto.NotificationTokenRequest
import com.gcc.miti.notification.service.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notification")
class NotificationController(private val notificationService: NotificationService) {
    @PutMapping("/token")
    fun putToken(@RequestBody request: NotificationTokenRequest,
                 @Parameter(hidden = true) @GetIdFromToken userId: String) {
        return notificationService.putToken(request, userId)
    }

    @Operation(summary = "알림 테스트")
    @PostMapping("/test")
    fun notificationTest(@RequestBody request: NotificationTestRequest) {
        return notificationService.notificationTest(request)
    }
}