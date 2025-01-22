package com.handong.cra.crawebbackend.exception.project;

import com.handong.cra.crawebbackend.exception.CraException;
import com.handong.cra.crawebbackend.exception.ErrorCode;

public class ProjectSemesterParseError extends CraException {
    public ProjectSemesterParseError() {
        super(ErrorCode.PROJECT_SEMESTER_PARSE_ERROR);
    }

}
