package com.gcc.miti.archive.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import java.time.LocalDate
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "deleted_user")
class DeletedUser(
    @Column(nullable = false, name = "email")
    val email: String,

    @Column(name = "birth_date")
    val birthDate: LocalDate
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}