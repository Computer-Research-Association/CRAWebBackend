package com.handong.cra.crawebbackend.exception.item;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class ItemIllegalCategoryException extends CraException {
    public ItemIllegalCategoryException() {
        super(ErrorCode.ITEM_ILLEGAL_CATEGORY);
    }
}
