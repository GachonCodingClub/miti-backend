package com.gcc.miti.notification.repository

import com.gcc.miti.notification.entity.UserNotification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserNotificationRepository: JpaRepository<UserNotification, String> {
}