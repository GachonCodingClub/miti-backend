package com.gcc.miti.archive.repository

import com.gcc.miti.archive.entity.DeletedGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeletedGroupRepository : JpaRepository<DeletedGroup, Long>