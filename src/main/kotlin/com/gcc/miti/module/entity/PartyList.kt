package com.gcc.miti.module.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "party_lists")
class PartyList() :
// @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
// val user: List<User>,

    BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var partyListId: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    val party: Party? = null
}
