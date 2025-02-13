package com.handong.cra.crawebbackend.exception.user;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class UserDormantUserLoginException extends CraException {
    public UserDormantUserLoginException() {
        super(ErrorCode.USER_DORMANT_USER_LOGIN);
    }
}
