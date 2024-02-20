package com.gcc.miti.module.repository

import com.gcc.miti.module.entity.DeletedUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeletedUserRepository : JpaRepository<DeletedUser, Long>