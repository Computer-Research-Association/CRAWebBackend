package com.handong.cra.crawebbackend.exception.board;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class BoardLikeBadRequestException extends CraException {
    public BoardLikeBadRequestException() {
        super(ErrorCode.BOARD_LIKE_BAD_REQUEST);
    }
}
