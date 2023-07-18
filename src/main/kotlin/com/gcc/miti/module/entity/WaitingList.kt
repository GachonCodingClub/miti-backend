package com.gcc.miti.module.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "waiting_list")
class WaitingList() : BaseTimeEntity() {

    var isCertified: Boolean = false

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var waitingListId: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    var group: Group? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    var party: Party? = null
}
