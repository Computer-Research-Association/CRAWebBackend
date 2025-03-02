package com.handong.cra.crawebbackend.exception.board;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class BoardPinDuplicateIdException extends CraException {
    public BoardPinDuplicateIdException() {
        super(ErrorCode.BOARD_PIN_DUPLICATED);
    }

}
