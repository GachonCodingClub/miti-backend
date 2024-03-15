package com.gcc.miti.notification.service

import com.gcc.miti.chat.entity.ChatMessage
import com.gcc.miti.group.entity.Group
import com.gcc.miti.group.entity.Party
import com.gcc.miti.notification.dto.NotificationTestRequest
import com.gcc.miti.notification.dto.NotificationTokenRequest
import com.gcc.miti.notification.entity.UserNotification
import com.gcc.miti.notification.repository.UserNotificationRepository
import com.gcc.miti.user.repository.UserRepository
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService(
    private val firebaseMessaging: FirebaseMessaging,
    private val userNotificationRepository: UserNotificationRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun putToken(request: NotificationTokenRequest, userId: String) {
        val user = userRepository.getReferenceById(userId)
        userNotificationRepository.deleteAllByToken(token = request.token)
        userNotificationRepository.save(UserNotification(user.userId, user, true, request.token))
    }


    @Transactional(readOnly = true)
    fun sendNewPartyRequestNotification(leaderUserId: String, group: Group) {
        val userNotification = userNotificationRepository.findByIdOrNull(leaderUserId) ?: return
        if (!userNotification.isAgreed) return
        val notification = Notification.builder()
            .setTitle(group.title)
            .setBody("새로운 참가 요청이 있습니다!")
            .build()
        val message = Message.builder()
            .setNotification(notification)
            .setToken(userNotification.token)
            .build()
        firebaseMessaging.sendAsync(message)
    }

    @Transactional(readOnly = true)
    fun sendNewChatNotification(chatMessage: ChatMessage) {
        val sender = chatMessage.user
        val receivers =
            chatMessage.group!!.acceptedUsers.toMutableList()
        // add group leader
        chatMessage.group?.leader?.let { receivers.add(it) }
        // remove sender from receivers
        receivers.remove(receivers.find { it.userId == sender.userId })

        val notification = Notification.builder()
            .setTitle(sender.nickname)
            .setBody(chatMessage.content.removePrefix("[MITI]"))
            .build()
        val messages = receivers.mapNotNull {
            val userNotification = it.userNotification
            if (userNotification?.isAgreed == true) {
                Message.builder()
                    .setNotification(notification)
                    .setToken(userNotification.token)
                    .putData("groupId", chatMessage.group!!.id.toString())
                    .build()
            } else {
                null
            }
        }
        if (messages.isEmpty()) return
        firebaseMessaging.sendEachAsync(messages)
    }

    fun sendPartyAcceptedNotification(group: Group, party: Party) {
        val notification = Notification.builder()
            .setTitle(group.title)
            .setBody("참가 요청이 수락되었습니다!")
            .build()
        val messages = party.partyMembers.mapNotNull {
            val userNotification = userNotificationRepository.findByIdOrNull(it.user?.userId) ?: return@mapNotNull null
            Message.builder()
                .setNotification(notification)
                .setToken(userNotification.token)
                .build()
        }
        if (messages.isEmpty()) return
        firebaseMessaging.sendEachAsync(messages)

    }

    fun notificationTest(request: NotificationTestRequest) {
        val notification = Notification.builder()
            .setTitle(request.title)
            .setBody(request.body)
            .build()
        val message = Message.builder()
            .setNotification(notification)
            .setToken(request.token)
            .build()
        firebaseMessaging.sendAsync(message)
    }
}