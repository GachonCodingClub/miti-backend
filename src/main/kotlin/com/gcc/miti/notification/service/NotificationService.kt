package com.gcc.miti.notification.service

import com.gcc.miti.chat.entity.ChatMessage
import com.gcc.miti.group.entity.Group
import com.gcc.miti.group.repository.GroupRepository
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
    private val userNotificationRepository: UserNotificationRepository, private val userRepository: UserRepository,
    private val groupRepository: GroupRepository
) {

    fun putToken(request: NotificationTokenRequest, userId: String) {
        val user = userRepository.getReferenceById(userId)
        userNotificationRepository.save(UserNotification(user.userId, user, true, request.token))
    }


    @Transactional(readOnly = true)
    fun sendNewPartyRequestNotification(leaderUserId: String, group: Group) {
        val userNotification = userNotificationRepository.findByIdOrNull(leaderUserId) ?: return
        if(!userNotification.isAgreed) return
        val notification = Notification.builder()
            .setTitle("${group.title}")
            .setBody("새로운 참가 요청이 있습니다!")
            .build()
        val message = Message.builder()
            .setNotification(notification)
            .setToken(userNotification.token)
            .build()
        println("send notification to $leaderUserId : ${userNotification.token}")
        firebaseMessaging.sendAsync(message)
    }

    @Transactional(readOnly = true)
    fun sendNewChatNotification(chatMessage: ChatMessage) {
        val sender = chatMessage.user
        val receivers = chatMessage.group!!.acceptedParties.flatMap { it.partyMember }.map { it.user!! }.toMutableList()
        receivers.add(chatMessage.group!!.leader)
        receivers.removeIf { it.userId == sender.userId }
        val notification = Notification.builder()
            .setTitle(sender.nickname)
            .setBody(chatMessage.content)
            .build()
        val messages = receivers.mapNotNull {
            val userNotification = userNotificationRepository.findByIdOrNull(it.userId)
            if(userNotification?.isAgreed == true){
                Message.builder()
                    .setNotification(notification)
                    .setToken(it.userNotification?.token)
                    .build()
            }else{
                null
            }
        }
        if(messages.isNotEmpty()){
            receivers.forEach {
                println("send notification to ${it.userId} : ${it.userNotification?.token}")
            }
            firebaseMessaging.sendAllAsync(messages)
        }
    }

//    @Scheduled(fixedDelay = 2500)
    fun sendNotificationTest(){
        val notification = Notification.builder()
            .setTitle("hi")
            .setBody("새로운 참가 요청이 있습니다!")
            .build()
        val message = Message.builder()
            .setNotification(notification)
            .setToken("eBLSaUWfR4iXZpxPGnTAyk:APA91bGlejfBpjhwmHRcrAgSHRIkTf_8sVgrbeP-_lTuSSokUBcHPdHIBDfKQcgSKP2zxhR6pGJg9hG-f5o9l70xgt47FrWd2WPxhSjzDPwgb7dlIF6yfPKFoYiy_8Hg2UZjgwKRRXGZ")
            .build()
        firebaseMessaging.send(message)
    }
}