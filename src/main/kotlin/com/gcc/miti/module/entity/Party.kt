package com.gcc.miti.module.entity

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "party")
class Party(
    val roomTitle: String,

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var partyId: Long = 0

    @ElementCollection
    var participants: List<String>? = null
}
