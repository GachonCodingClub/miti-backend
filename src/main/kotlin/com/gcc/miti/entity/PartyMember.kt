package com.gcc.miti.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "party_member")
class PartyMember :
    com.gcc.miti.entity.BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: com.gcc.miti.entity.User? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    var party: com.gcc.miti.entity.Party? = null
}
