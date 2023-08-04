package com.example.pncTest.model.enums;

public enum ErrorCode {
    VALIDATION_ERROR(400),
    SERVER_ERROR(500),
    INELIGIBLE_ERROR(403);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}