package com.gcc.miti.common.webhook

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class DiscordWebhook {
    private val restClient = RestClient.create()

    @Value("\${DISCORD_WEBHOOK_URL}")
    lateinit var discordWebhookUrl: String

    fun sendDiscordWebhook(request: DiscordWebhookRequest) {
        restClient.post().uri(discordWebhookUrl).body(request)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
    }
}