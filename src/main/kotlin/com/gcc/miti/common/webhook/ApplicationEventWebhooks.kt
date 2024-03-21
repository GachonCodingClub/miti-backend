package com.gcc.miti.common.webhook

import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class ApplicationEventWebhooks(
    private val discordWebhook: DiscordWebhook
): DisposableBean {
    @Value("\${spring.profiles.active}")
    private lateinit var activeProfile: String


    fun isLocal(): Boolean {
        return activeProfile == "local"
    }

    @EventListener(ApplicationReadyEvent::class)
    fun applicationReady() {
        if (isLocal()) return
        discordWebhook.sendDiscordWebhook(
            DiscordWebhookRequest(
                "서버 시작",
                embeds = listOf(
                    Embed(
                        "서버 시작",
                        "${
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        } UTC - MITI BOT"
                    )
                )
            )
        )
    }

    // every 30 minutes
    @Scheduled(cron = "0 0/30 * * * *")
    fun sendHealthy() {
        if (isLocal()) return
        discordWebhook.sendDiscordWebhook(
            DiscordWebhookRequest(
                "미티 서버 HealthCheck",
                embeds = listOf(
                    Embed(
                        "OK",
                        "${
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        } UTC - MITI BOT - 건강해요 "
                    )
                )
            )
        )
    }

    override fun destroy() {
        if(isLocal()) return
        discordWebhook.sendDiscordWebhook(
            DiscordWebhookRequest(
                "서버 종료",
                embeds = listOf(
                    Embed(
                        "서버 종료",
                        "${
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        } UTC - MITI BOT"
                    )
                )
            )
        )
    }


}