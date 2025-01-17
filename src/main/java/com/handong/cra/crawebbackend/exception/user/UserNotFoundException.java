package com.handong.cra.crawebbackend.exception.user;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class UserNotFoundException extends CraException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND_ERROR);
    }
}
