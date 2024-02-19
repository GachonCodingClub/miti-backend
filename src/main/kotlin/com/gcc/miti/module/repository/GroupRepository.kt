package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.Group
import com.gcc.miti.module.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface GroupRepository : JpaRepository<Group, Long> {
    fun getByLeaderAndId(leader: User, id: Long): Group?

    @Query(value = """select distinct g from Group g left join Party p on g.id = p.group.id and p.partyStatus = 'ACCEPTED'
        left join PartyMember pm on p.id = pm.party.id
        where g.leader.userId = :userId or pm.user.userId = :userId """)
    fun findMyGroups(@Param("userId") userId: String, pageable: Pageable): Page<Group>

    fun findAllByMeetDateIsBefore(localDateTime: LocalDateTime): List<Group>
}
