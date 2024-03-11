package com.gcc.miti.common.discord

class DiscordWebhookRequest(
    val content: String,
    val tts: Boolean = false,
    val embeds: List<Embed>
)

class Embed(
    val title: String,
    val description: String
)