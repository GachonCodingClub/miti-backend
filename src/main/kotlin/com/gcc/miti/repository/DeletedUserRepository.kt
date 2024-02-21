package com.gcc.miti.repository

import com.gcc.miti.entity.DeletedUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeletedUserRepository : JpaRepository<DeletedUser, Long>