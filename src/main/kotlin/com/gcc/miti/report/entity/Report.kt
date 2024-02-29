package com.gcc.miti.report.entity

import jakarta.persistence.*

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}