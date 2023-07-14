package com.gcc.miti.module.entity

import org.hibernate.annotations.Formula
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "my_group")
class Group(
    val description: String,
    val title: String,
    val maxUsers: Long,

) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var meetDate: LocalDateTime? = null

    var meetPlace: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_id")
    var leader: User? = null

    @Formula(
        "(SELECT COUNT(*) \n" +
            "FROM waiting_list wl, party_lists pl \n" +
            "WHERE wl.group_id = id \n" +
            "  AND wl.party_id = pl.party_id) ",
    )
    var userCount: Long = 0
}
