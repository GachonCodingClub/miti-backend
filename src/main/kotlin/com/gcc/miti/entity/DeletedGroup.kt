package com.gcc.miti.entity

import com.gcc.miti.constants.GroupStatus
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "deleted_group")
class DeletedGroup(
    @Column(name = "description")
    var description: String,

    @Column(name = "title")
    var title: String,

    @Column(name = "max_users")
    val maxUsers: Int,

    @Enumerated(EnumType.STRING)
    var groupStatus: GroupStatus,

    @Column(name = "meet_date")
    var meetDate: LocalDateTime?,

    @Column(name = "meet_place")
    var meetPlace: String?,

    @Column(name = "user_count_when_deleted")
    val userCountWhenDeleted: Int

):BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    companion object{
        fun toDeletedGroup(group:Group): DeletedGroup {
            with(group){
                return DeletedGroup(
                    description,
                    title,
                    maxUsers,
                    groupStatus,
                    meetDate,
                    meetPlace,
                    1 + acceptedParties.flatMap { it.partyMember }.size
                )
            }
        }
    }
}
