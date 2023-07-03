package com.gcc.miti.module.global.exception

import org.springframework.http.HttpStatus

enum class BaseExceptionCode(val httpStatusCode: Int, val message: String) {
    // 400 BAD REQUEST
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.BAD_REQUEST.value(), "리프레쉬 토큰이 일치하지 않습니다."),
    AUTHORIZATION_HEADER_NULL(HttpStatus.BAD_REQUEST.value(), "인증 헤더가 null입니다."),

    // 403 FORBIDDEN

    // 404 NOT FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않습니다."),

    // 409 CONFLICT
    USER_ID_CONFLICT(HttpStatus.CONFLICT.value(), "아이디가 중복입니다."),
}
