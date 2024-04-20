package com.gcc.miti.common

import com.gcc.miti.common.exception.BaseException
import com.gcc.miti.common.exception.BaseExceptionCode
import org.springframework.data.repository.CrudRepository

fun <T, ID> CrudRepository<T, ID>.findByIdOrThrowNotFound(id: ID): T {
    return findById(id!!).orElseThrow {
        BaseException(BaseExceptionCode.NOT_FOUND)
    }
}