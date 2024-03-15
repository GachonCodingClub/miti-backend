package com.gcc.miti.report.service

import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import com.gcc.miti.common.webhook.DiscordWebhook
import com.gcc.miti.common.webhook.DiscordWebhookRequest
import com.gcc.miti.common.webhook.Embed
import com.gcc.miti.report.dto.ReportReq
import com.gcc.miti.report.entity.Report
import com.gcc.miti.report.repository.ReportRepository
import com.gcc.miti.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val userRepository: UserRepository,
    private val discordWebhook: DiscordWebhook
) {
    fun report(request: ReportReq, userId: String){
        val targetUser = userRepository.findByNickname(request.targetUserNickname) ?: throw BaseException(BaseExceptionCode.NOT_FOUND)
        reportRepository.save(Report(request.content, userId, targetUser.userId))
        discordWebhook.sendDiscordWebhook(DiscordWebhookRequest(
            content = "누군가 유저를 신고했어요!",
            embeds = listOf(Embed("신고자", userId), Embed("신고 대상", targetUser.userId), Embed("신고 사유", request.content))
        ))
    }
}