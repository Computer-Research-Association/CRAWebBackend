package com.handong.cra.crawebbackend.exception.project;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class ProjectPageSizeLimitExceededException extends CraException {
    public ProjectPageSizeLimitExceededException() {
        super(ErrorCode.PROJECT_PAGE_SIZE_LIMIT_EXCEEDED);
    }
}
