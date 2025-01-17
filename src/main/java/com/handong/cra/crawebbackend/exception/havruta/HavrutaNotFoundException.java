package com.handong.cra.crawebbackend.exception.havruta;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class HavrutaNotFoundException extends CraException {
    public HavrutaNotFoundException() {
        super(ErrorCode.HAVRUTA_NOT_FOUND);
    }
}
