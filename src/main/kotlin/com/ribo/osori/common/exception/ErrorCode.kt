package com.ribo.osori.common.exception

import com.fasterxml.jackson.annotation.JsonValue
import org.springframework.http.HttpStatus

enum class ErrorCode(
    @JsonValue
    val status: HttpStatus,
    val code: String,
    val message: String,
) {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", " 잘못된 입력 값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", " 메소드를 사용할 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", " 서버 에러입니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", " 잘못된 타입입니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", " 접근 권한이 없습니다."),

    // User
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "U001", "이메일 또는 비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "U002", "중복된 이메일입니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "U003", "존재하지 않는 사용자입니다."),

    // Auth
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "유효하지 않은 토큰입니다."),
    NOT_FOUND_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "로그인이 필요합니다."),
    BLACKLIST_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "허용되지 않는 토큰입니다."),
    INVALID_TOKEN_REQUEST(HttpStatus.UNAUTHORIZED, "A004", "토큰을 재발급 할 수 없는 상태입니다."),
}