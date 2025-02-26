package com.handong.cra.crawebbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class CraExceptionHandler {
    @ExceptionHandler(CraException.class)
    public ResponseEntity<ErrorResponse> craException(CraException e) {
        log.error("CraException", e);

        return createErrorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> commonException(Exception e) {
        log.error("CommonException", e);
        return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(Exception e) {
        log.error("NotFoundException", e);
        return createErrorResponseEntity(ErrorCode.NOT_FOUND_ERROR);
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        return createErrorResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode) {
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode),
                errorCode.getStatus()
        );
    }
}
