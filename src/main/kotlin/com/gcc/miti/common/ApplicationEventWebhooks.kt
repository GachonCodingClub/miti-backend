package com.gcc.miti.common

import com.gcc.miti.common.discord.DiscordWebhookRequest
import com.gcc.miti.common.discord.Embed
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@Component
class ApplicationEventWebhooks {
    @Value("\${spring.profiles.active}")
    private lateinit var activeProfile: String

    @Value("\${DISCORD_WEBHOOK_URL}")
    lateinit var discordWebhookUrl: String

    val restClient = RestClient.create()

    fun isLocal(): Boolean {
        return activeProfile == "local"
    }

    @EventListener(ApplicationReadyEvent::class)
    fun applicationReady() {
        if (isLocal()) return
        sendDiscordWebhook(
            DiscordWebhookRequest(
                "배포 완료",
                embeds = listOf(
                    Embed(
                        "배포 완료",
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
        sendDiscordWebhook(
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

    fun sendDiscordWebhook(request: DiscordWebhookRequest) {
        restClient.post().uri(discordWebhookUrl).body(request)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
    }
}