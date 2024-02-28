package com.gcc.miti.report.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "report")
class Report(
    @Column(nullable = false, name = "content")
    val content: String,

    @Column(nullable = false, name = "reporter_user_id")
    val reporterUserId: String,

    @Column(nullable = false, name = "report_target_user_id")
    val reportTargetUserId: String
) {
    @Id
    @GeneratedValue
    var id: Long = 0
}