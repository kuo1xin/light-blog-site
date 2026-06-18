package com.lightblog.common;

public class BusinessException extends RuntimeException {
    private final int code;
    private final int httpStatus;

    public BusinessException(int code, String message) {
        this(code, message, 400);
    }

    public BusinessException(int code, String message, int httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
