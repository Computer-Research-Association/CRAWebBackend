package com.handong.cra.crawebbackend.exception.item;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class ItemNotFoundException extends CraException {
    public ItemNotFoundException() {
        super(ErrorCode.ITEM_NOT_FOUND);
    }
}
