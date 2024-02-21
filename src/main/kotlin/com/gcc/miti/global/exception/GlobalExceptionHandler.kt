package com.gcc.miti.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(com.gcc.miti.global.exception.BaseException::class)
    fun baseExceptionHandler(e: com.gcc.miti.global.exception.BaseException): ResponseEntity<com.gcc.miti.global.exception.ExceptionResponse> {
        return ResponseEntity.status(e.baseExceptionCode.httpStatusCode)
            .body(
                com.gcc.miti.global.exception.ExceptionResponse(
                    e.baseExceptionCode.httpStatusCode,
                    e.baseExceptionCode.message
                )
            )
    }
}
