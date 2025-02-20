package com.handong.cra.crawebbackend.exception.account;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class AccountUserAlreadyExistsException extends CraException {
    public AccountUserAlreadyExistsException() {
        super(ErrorCode.ACCOUNT_USER_ALREADY_EXIST);
    }
}
