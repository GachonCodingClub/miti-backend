package com.gcc.miti.group.entity

import com.gcc.miti.common.entity.BaseTimeEntity
import com.gcc.miti.group.constants.PartyStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

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
    var partyMembers: MutableList<PartyMember> = mutableListOf()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    var group: Group? = null
}
