package com.karpo.quisy.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NOT_FOUND_USER("E000", "유저를 찾을 수 없습니다"),
    UNKNOWN_ERROR("E999", "알 수 없는 에러");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

