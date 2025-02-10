package com.handong.cra.crawebbackend.exception.auth;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class AuthInvalidTokenException extends CraException {
    public AuthInvalidTokenException() {
        super(ErrorCode.AUTH_INVALID_TOKEN);
    }
}
