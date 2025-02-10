package com.handong.cra.crawebbackend.exception.auth;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class AuthTokenExpiredException extends CraException {
    public AuthTokenExpiredException() {
        super(ErrorCode.AUTH_TOKEN_EXPIRED);
    }
}
