package com.handong.cra.crawebbackend.exception.board;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class PageSizeLimitExceededException extends CraException {
    public PageSizeLimitExceededException() {
        super(ErrorCode.PAGE_SIZE_LIMIT_EXCEEDED);
    }
}
