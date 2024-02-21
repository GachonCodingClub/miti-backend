package com.gcc.miti.archive.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

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