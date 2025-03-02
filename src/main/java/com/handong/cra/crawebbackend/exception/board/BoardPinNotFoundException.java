package com.handong.cra.crawebbackend.exception.board;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class BoardPinNotFoundException extends CraException {
    public BoardPinNotFoundException() {
        super(ErrorCode.BOARD_PIN_NOT_FOUND);
    }

}
