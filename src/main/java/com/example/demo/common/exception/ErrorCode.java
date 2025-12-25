package com.example.demo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// common/exception/ErrorCode.java
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Member (M으로 시작하는 코드)
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "M001", "이미 사용 중인 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M002", "존재하지 않는 회원입니다."),

    // Global (C로 시작하는 공통 코드)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버 내부 오류입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}