package com.gcc.miti.notification

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource


@Configuration
class FCMConfig {
    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        val resource = ClassPathResource("firebase/miti-app-firebase.json")
        val file = resource.inputStream
            val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(file))
                .build()

        val firebaseApp = FirebaseApp.initializeApp(options)
        return FirebaseMessaging.getInstance(firebaseApp)
    }
}