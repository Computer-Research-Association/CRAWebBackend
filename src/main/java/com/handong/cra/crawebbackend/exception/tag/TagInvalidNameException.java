package com.handong.cra.crawebbackend.exception.tag;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class TagInvalidNameException extends CraException {
    public TagInvalidNameException() {
        super(ErrorCode.TAG_INVALID_NAME);
    }
}
