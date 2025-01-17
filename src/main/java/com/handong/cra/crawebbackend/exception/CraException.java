package com.handong.cra.crawebbackend.exception;

import lombok.Getter;

@Getter
public abstract class CraException extends RuntimeException {
    private final ErrorCode errorCode;

    public CraException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CraException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
