package com.handong.cra.crawebbackend.exception.board;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class BoardIllegalCategoryException extends CraException {
    public BoardIllegalCategoryException() {
        super(ErrorCode.BOARD_ILLEGAL_CATEGORY);
    }
}
