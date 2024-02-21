package com.gcc.miti.repository

import com.gcc.miti.entity.DeletedGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeletedGroupRepository : JpaRepository<DeletedGroup, Long>