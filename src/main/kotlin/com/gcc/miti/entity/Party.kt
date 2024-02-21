package com.gcc.miti.entity

import com.gcc.miti.constants.PartyStatus
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "party")
class Party(
    @Enumerated(EnumType.STRING)
    var partyStatus: PartyStatus = PartyStatus.WAITING,

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "party", cascade = [CascadeType.ALL], orphanRemoval = true)
    var partyMember: MutableList<PartyMember> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    var group: Group? = null
}
