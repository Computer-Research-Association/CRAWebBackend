package com.handong.cra.crawebbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E1", "서버 에러가 발생하였습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E2", "잘못된 HTTP Method입니다."),

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "B1", "존재하지 않는 게시글입니다."),
    BOARD_LIKE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "B2", "잘못된 게시글 좋아요 요청입니다."),
    BOARD_ILLEGAL_CATEGORY(HttpStatus.BAD_REQUEST, "B3", "잘못된 게시글 카테고리입니다."),
    BOARD_PAGE_SIZE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "B4", "비정상적으로 큰 Per page 값입니다."),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C1", "존재하지 않는 댓글입니다."),
    COMMENT_NESTED_REPLY_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "C2", "2차 대댓글은 허용되지 않습니다."),

    HAVRUTA_NOT_FOUND(HttpStatus.NOT_FOUND, "H1", "존재하지 않는 하브루타입니다."),

    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "I1", "존재하지 않는 비품입니다."),
    ITEM_ILLEGAL_CATEGORY(HttpStatus.BAD_REQUEST, "I2", "잘못된 비품 카테고리입니다."),
    ITEM_PAGE_SIZE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "I3", "비정상적으로 큰 Per page 값입니다."),

    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "P1", "존재하지 않는 프로젝트입니다."),
    PROJECT_PAGE_SIZE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "P2", "비정상적으로 큰 Per page 값입니다."),
    PROJECT_SEMESTER_PARSE(HttpStatus.BAD_REQUEST, "P3", "잘못된 학기 수 값 입니다. EX) 25-1"),

    USER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "U1", "해당하는 유저가 존재하지 않습니다."),

    ACCOUNT_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "A1", "해당하는 코드가 존재하지 않습니다."),
    ACCOUNT_CODE_EXPIRED(HttpStatus.GONE, "A2", "만료된 코드입니다"),

    S3IMAGE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "S3I1","이미지 업로드중 에러가 발생했습니다."),
    S3IMAGE_TRANSFER(HttpStatus.INTERNAL_SERVER_ERROR, "S3I2","이미지 경로 이동중 에러가 발생했습니다."),
    S3IMAGE_URL(HttpStatus.BAD_REQUEST,  "S3I3","잘못된 이미지 url 입니다");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
