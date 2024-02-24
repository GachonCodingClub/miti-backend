package com.gcc.miti.report.controller

import com.gcc.miti.report.dto.ReportReq
import com.gcc.miti.report.service.ReportService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/report")
class ReportController(private val reportService: ReportService) {

    @PostMapping("")
    fun report(@RequestBody request: ReportReq){
        return reportService.report(request)
    }
}