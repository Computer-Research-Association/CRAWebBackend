package com.handong.cra.crawebbackend.exception.auth;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthTokenExpiredException extends CraException {
    public AuthTokenExpiredException() {
        super(ErrorCode.AUTH_TOKEN_EXPIRED);
    }
}
