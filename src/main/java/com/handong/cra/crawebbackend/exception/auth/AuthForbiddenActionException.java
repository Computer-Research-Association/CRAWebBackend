package com.handong.cra.crawebbackend.exception.auth;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class AuthForbiddenActionException extends CraException {
    public AuthForbiddenActionException() {
        super(ErrorCode.AUTH_FORBIDDEN_ACTION);
    }
}
