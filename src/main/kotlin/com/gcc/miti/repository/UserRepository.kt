package com.gcc.miti.repository

import com.gcc.miti.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun findAllByNicknameIn(nicknames: List<String>): List<User>

    fun existsByNickname(nickname:String):Boolean
}
