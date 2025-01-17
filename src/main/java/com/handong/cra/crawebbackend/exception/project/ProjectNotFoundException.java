package com.handong.cra.crawebbackend.exception.project;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class ProjectNotFoundException extends CraException {
    public ProjectNotFoundException() {
        super(ErrorCode.PROJECT_NOT_FOUND);
    }
}
