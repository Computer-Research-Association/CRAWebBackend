package com.handong.cra.crawebbackend.exception.account;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class AccountCodeExpiredException extends CraException {
    public AccountCodeExpiredException() {
        super(ErrorCode.ACCOUNT_CODE_EXPIRED);
    }
}
