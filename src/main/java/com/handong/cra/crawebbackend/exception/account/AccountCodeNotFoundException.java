package com.handong.cra.crawebbackend.exception.account;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class AccountCodeNotFoundException extends CraException {
    public AccountCodeNotFoundException() {
        super(ErrorCode.ACCOUNT_CODE_NOT_FOUND);
    }
}
