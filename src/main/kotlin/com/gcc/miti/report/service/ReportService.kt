package com.gcc.miti.report.service

import com.gcc.miti.auth.security.SecurityUtils
import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import com.gcc.miti.report.dto.ReportReq
import com.gcc.miti.report.entity.Report
import com.gcc.miti.report.repository.ReportRepository
import com.gcc.miti.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ReportService(
    private val reportRepository: ReportRepository,
    private val userRepository: UserRepository
) {
    fun report(request: ReportReq, userId: String){
        val targetUser = userRepository.findByNickname(request.targetUserNickname) ?: throw BaseException(BaseExceptionCode.NOT_FOUND)
        reportRepository.save(Report(request.content, userId, targetUser.userId))
    }
}