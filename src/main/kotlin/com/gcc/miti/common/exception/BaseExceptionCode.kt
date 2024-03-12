package com.gcc.miti.common.exception

import org.springframework.http.HttpStatus

enum class BaseExceptionCode(val httpStatusCode: Int, val message: String) {

    // 400 BAD REQUEST
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.BAD_REQUEST.value(), "리프레쉬 토큰이 일치하지 않습니다."),
    AUTHORIZATION_HEADER_NULL(HttpStatus.BAD_REQUEST.value(), "인증 헤더가 null입니다."),
    NOT_UNIVERSITY_EMAIL(HttpStatus.BAD_REQUEST.value(), "지원하는 대학교 이메일이 아닙니다."),

    // 401 UNAUTHORIZIED
    NOT_CERTIFIED(HttpStatus.UNAUTHORIZED.value(), "미인증 상태입니다."),

    // 403 FORBIDDEN
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "권한이 없습니다."),
    BANNED_USER(HttpStatus.FORBIDDEN.value(), "영구 정지된 유저입니다."),

    // 404 NOT FOUND
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "아이디가 존재하지 않습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "존재하지 않습니다."),

    // 409 CONFLICT
    ALREADY_REGISTERED(HttpStatus.CONFLICT.value(), "이미 가입되었습니다."),
    USER_ID_CONFLICT(HttpStatus.CONFLICT.value(), "아이디가 중복입니다."),
    NICKNAME_CONFLICT(HttpStatus.CONFLICT.value(), "닉네임 중복입니다."),
    MAX_USER_ERROR(HttpStatus.CONFLICT.value(), "정원이 초과하였습니다."),
    ALREADY_BLOCKED(HttpStatus.CONFLICT.value(), "이미 차단한 유저입니다."),
}
