package com.handong.cra.crawebbackend.exception.auth;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class AuthLoginFailException extends CraException {
    public AuthLoginFailException() {
        super(ErrorCode.AUTH_LOGIN_FAIL);
    }
}
