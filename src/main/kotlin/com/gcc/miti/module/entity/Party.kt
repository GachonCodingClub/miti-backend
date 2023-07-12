package com.gcc.miti.module.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "partys")
class Party(
    val roomTitle: String,
    val maxUser: Long,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var partyId: Long = 0

    var cntUser: Long = 0
}
