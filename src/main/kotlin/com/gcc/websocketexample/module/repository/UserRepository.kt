package com.gcc.websocketexample.module.repository

import com.gcc.websocketexample.module.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String>
