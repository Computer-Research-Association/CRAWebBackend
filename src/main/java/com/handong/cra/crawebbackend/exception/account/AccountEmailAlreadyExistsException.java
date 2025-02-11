package com.handong.cra.crawebbackend.exception.account;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class AccountEmailAlreadyExistsException extends CraException {
    public AccountEmailAlreadyExistsException() {
        super(ErrorCode.AUTH_EMAIL_ALREADY_EXIST);
    }
}
