package com.handong.cra.crawebbackend.exception.comment;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class CommentNestedReplyNotAllowedException extends CraException {
    public CommentNestedReplyNotAllowedException() {
        super(ErrorCode.COMMENT_NESTED_REPLY_NOT_ALLOWED);
    }
}
