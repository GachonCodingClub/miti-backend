package com.gcc.miti.notification.service

import com.gcc.miti.auth.security.SecurityUtils
import com.gcc.miti.chat.repository.ChatMessageRepository
import com.gcc.miti.notification.entity.UserNotification
import com.gcc.miti.notification.repository.UserNotificationRepository
import com.gcc.miti.user.repository.UserRepository
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService(
    private val firebaseMessaging: FirebaseMessaging,
    private val userNotificationRepository: UserNotificationRepository, private val userRepository: UserRepository,
    private val chatMessageRepository: ChatMessageRepository
) {

    fun putToken(token: String) {
        val user = userRepository.getReferenceById(SecurityUtils.getUserIdFromJwt())
        userNotificationRepository.save(UserNotification(user.userId, user, false, token))
    }

    fun sendNotification(userId: String) {
        val notification = Notification.builder()
            .setTitle("")
            .setBody("으억")
            .build()
        val message = Message.builder()
            .setNotification(notification)
            .setToken("eBLSaUWfR4iXZpxPGnTAyk:APA91bGlejfBpjhwmHRcrAgSHRIkTf_8sVgrbeP-_lTuSSokUBcHPdHIBDfKQcgSKP2zxhR6pGJg9hG-f5o9l70xgt47FrWd2WPxhSjzDPwgb7dlIF6yfPKFoYiy_8Hg2UZjgwKRRXGZ")
            .build()
        firebaseMessaging.send(message)
    }

    @Async
    @Transactional(readOnly = true)
    fun sendNewChatNotification(chatMessageId: Long) {
        val chatMessage = chatMessageRepository.findByIdOrNull(chatMessageId)
        val sender = chatMessage!!.user
        val receivers = chatMessage.group!!.acceptedParties.flatMap { it.partyMember }.map { it.user!! }.toMutableList()
        receivers.add(chatMessage.group!!.leader)
        receivers.removeIf { it.userId == sender.userId }
        val notification = Notification.builder()
            .setTitle(sender.nickname)
            .setBody(chatMessage.content)
            .build()
        val messages = receivers.mapNotNull {
            if(it.userNotification?.isAgreed == true && it.userNotification?.token != null){
                Message.builder()
                    .setNotification(notification)
                    .setToken(it.userNotification?.token)
                    .build()
            }else{
                null
            }
        }
        if(messages.isNotEmpty()){
            firebaseMessaging.sendAll(messages)
        }
    }
}