package com.gcc.miti.module.entity

import javax.persistence.DiscriminatorColumn
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "waiting_list")
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.JOINED)
class WaitingList() : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    var group: Group? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    var party: Party? = null
}
