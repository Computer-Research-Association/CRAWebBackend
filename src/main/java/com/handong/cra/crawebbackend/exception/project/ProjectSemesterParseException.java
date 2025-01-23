package com.handong.cra.crawebbackend.exception.project;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class ProjectSemesterParseException extends CraException {
    public ProjectSemesterParseException() {
        super(ErrorCode.PROJECT_SEMESTER_PARSE);
    }

}
