package com.gcc.miti.chat.repository

import com.gcc.miti.chat.entity.LastReadChatMessage
import com.gcc.miti.group.entity.Group
import com.gcc.miti.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface LastReadChatMessageRepository: JpaRepository<LastReadChatMessage, Long>{
    fun findByGroupAndUser(group: Group, user: User): LastReadChatMessage?

    @Query("""
        select l from LastReadChatMessage l where l.group.id in (:groupIds) and l.user = :user 
    """)
    fun findAllByGroupIdsAndUser(@Param("groupIds") groupIds: List<Long>, @Param("user") user: User): List<LastReadChatMessage>
}