package com.gcc.miti.report.repository

import com.gcc.miti.report.entity.Report
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReportRepository: JpaRepository<Report, Long>