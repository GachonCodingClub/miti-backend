package com.gcc.miti.module.entity

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "party")
class Party(
    val roomTitle: String,

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var partyId: Long = 0

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "party", cascade = [CascadeType.ALL], orphanRemoval = true)
    val partyList: MutableList<PartyList> = mutableListOf()
}
