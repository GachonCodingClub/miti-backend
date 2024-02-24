package com.gcc.miti.user.repository

import com.gcc.miti.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun findAllByNicknameIn(nicknames: List<String>): List<User>

    fun existsByNickname(nickname:String):Boolean

    fun findByNickname(nickname:String): User?
}
