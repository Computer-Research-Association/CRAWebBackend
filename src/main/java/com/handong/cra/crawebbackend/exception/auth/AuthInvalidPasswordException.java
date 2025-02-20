package com.handong.cra.crawebbackend.exception.auth;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;


public class AuthInvalidPasswordException extends CraException {
    public AuthInvalidPasswordException() {
        super(ErrorCode.BAD_REQUEST_ERROR);
    }
}
