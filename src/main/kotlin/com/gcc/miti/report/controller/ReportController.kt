package com.gcc.miti.report.controller

import com.gcc.miti.auth.security.GetIdFromToken
import com.gcc.miti.report.dto.ReportReq
import com.gcc.miti.report.service.ReportService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/report")
class ReportController(private val reportService: ReportService) {

    @Operation(summary = "신고")
    @PostMapping("")
    fun report(@RequestBody request: ReportReq,
               @Parameter(hidden = true) @GetIdFromToken userId: String){
        return reportService.report(request, userId)
    }
}