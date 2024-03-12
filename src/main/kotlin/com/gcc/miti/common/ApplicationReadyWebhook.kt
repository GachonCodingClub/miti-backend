package com.gcc.miti.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.gcc.miti.common.discord.DiscordWebhookRequest
import com.gcc.miti.common.discord.Embed
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class ApplicationReadyWebhook() {
    @Value("\${spring.profiles.active}")
    private lateinit var activeProfile: String

    @Value("\${DISCORD_WEBHOOK_URL}")
    lateinit var discordWebhookUrl: String

    @EventListener(ApplicationReadyEvent::class)
    fun applicationReady() {
        if (activeProfile == "local") return
        val restClient = RestClient.create()
        val objectMapper = ObjectMapper()
        val discordWebHookRequest = objectMapper.writeValueAsString(
            DiscordWebhookRequest(
                "배포 완료",
                embeds = listOf(
                    Embed(
                        "배포 완료",
                        "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))} - MITI BOT"
                    )
                )
            )
        )
        restClient.post().uri(discordWebhookUrl).body(discordWebHookRequest)
            .header(HttpHeaders.CONTENT_TYPE, "application/json").retrieve()
    }
}