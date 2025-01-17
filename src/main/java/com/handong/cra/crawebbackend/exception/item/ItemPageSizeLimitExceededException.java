package com.handong.cra.crawebbackend.exception.item;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class ItemPageSizeLimitExceededException extends CraException {
    public ItemPageSizeLimitExceededException() {
        super(ErrorCode.ITEM_PAGE_SIZE_LIMIT_EXCEEDED);
    }
}
