package com.gcc.miti.archive.repository

import com.gcc.miti.archive.entity.DeletedUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeletedUserRepository : JpaRepository<DeletedUser, Long>