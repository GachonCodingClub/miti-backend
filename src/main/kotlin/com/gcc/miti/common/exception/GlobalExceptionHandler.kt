package com.gcc.miti.common.exception

import com.gcc.miti.common.webhook.DiscordWebhook
import com.gcc.miti.common.webhook.DiscordWebhookRequest
import com.gcc.miti.common.webhook.Embed
import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.RestClient

@RestControllerAdvice
class GlobalExceptionHandler(
    private val discordWebhook: DiscordWebhook
) {
    @Value("\${spring.profiles.active}")
    lateinit var profile: String

    fun isLocal(): Boolean {
        return profile == "local"
    }
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Value("\${DISCORD_WEBHOOK_URL}")
    lateinit var discordWebhookUrl: String

    @ExceptionHandler(BaseException::class)
    fun baseExceptionHandler(e: BaseException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity.status(e.baseExceptionCode.httpStatusCode)
            .body(
                ExceptionResponse(
                    e.baseExceptionCode.httpStatusCode,
                    e.baseExceptionCode.message
                )
            )
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun badCredentialsExceptionHandler(e: BadCredentialsException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "아이디 혹은 비밀번호가 잘못됐습니다."))
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun entityNotFoundExceptionHandler(e: EntityNotFoundException): ResponseEntity<ExceptionResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse(HttpStatus.NOT_FOUND.value(), "요청하신 정보가 존재하지 않습니다."))
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(e: Exception): ResponseEntity<ExceptionResponse> {
        logger.error(e.stackTraceToString())
        if (!isLocal()) {
            sendDiscordMessage(e)
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ExceptionResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.message ?: "Internal Server Error"
            )
        )
    }

    private fun sendDiscordMessage(e: Exception) {
        discordWebhook.sendDiscordWebhook(DiscordWebhookRequest(
            e.message ?: "Internal Server Error",
            embeds = listOf(Embed("stacktrace", e.getSlimStackTrace()))
        ))
    }

    fun Throwable.getSlimStackTrace() = this.javaClass.name + "\n" + this.message + "\n" + this.stackTrace.map { it.toString() }
        .filter { !it.startsWith("org.springframework.") }
        .filter { !it.startsWith("java.base/") }
        .filter { !it.startsWith("jdk.proxy") }
        .filter { !it.startsWith("org.apache") }
        .joinToString("\n")
}
