package com.gcc.miti.notification.controller

import com.gcc.miti.notification.service.NotificationService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notification")
class NotificationController(private val notificationService: NotificationService) {
    @PutMapping("/token")
    fun putToken(@RequestParam("token", required = true) token: String) {
        return notificationService.putToken(token)
    }
}