package com.handong.cra.crawebbackend.exception.user;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class UserInvalidPasswordException extends CraException {
    public UserInvalidPasswordException() {
        super(ErrorCode.USER_INVALID_PASSWORD);
    }
}
