package com.handong.cra.crawebbackend.exception.board;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class BoardPageSizeLimitExceededException extends CraException {
    public BoardPageSizeLimitExceededException() {
        super(ErrorCode.BOARD_PAGE_SIZE_LIMIT_EXCEEDED);
    }
}
