package com.handong.cra.crawebbackend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E1", "서버 에러가 발생하였습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "E2", "잘못된 HTTP Method 입니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "E3", "잘못된 URL 요청입니다."),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "E4", "잘못된 요청입니다."),

    PAGE_SIZE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "PG1", "비정상적으로 큰 Per page 값입니다."),
    
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "B1", "존재하지 않는 게시글입니다."),
    BOARD_LIKE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "B2", "잘못된 게시글 좋아요 요청입니다."),
    BOARD_ILLEGAL_CATEGORY(HttpStatus.BAD_REQUEST, "B3", "잘못된 게시글 카테고리입니다."),

    BOARD_PIN_NOT_FOUND(HttpStatus.NOT_FOUND, "BP1", "존재하지 않는 고정 게시글입니다."),
    BOARD_PIN_DUPLICATED(HttpStatus.BAD_REQUEST, "BP2", "이미 지정된 고정 게시글 입니다."),


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
    USER_INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "U2", "변경하려는 패스워드가 잘못되었습니다."),
    USER_DORMANT_USER_LOGIN (HttpStatus.FORBIDDEN, "U3", "휴면 계정입니다"),

    ACCOUNT_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "A1", "해당하는 코드가 존재하지 않습니다."),
    ACCOUNT_CODE_EXPIRED(HttpStatus.GONE, "A2", "만료된 코드입니다"),
    ACCOUNT_EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "A3", "이미 사용중인 이메일 입니다."),
    ACCOUNT_USER_ALREADY_EXIST(HttpStatus.CONFLICT, "A4", "이미 사용중인 유저 아이디 입니다."),

    S3IMAGE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "S3I1","이미지 업로드중 에러가 발생했습니다."),
    S3IMAGE_TRANSFER(HttpStatus.INTERNAL_SERVER_ERROR, "S3I2","이미지 경로 이동중 에러가 발생했습니다."),
    S3IMAGE_URL(HttpStatus.BAD_REQUEST,  "S3I3","잘못된 이미지 url 입니다"),

    AUTH_LOGIN_FAIL (HttpStatus.UNAUTHORIZED,  "AU0","로그인 실패."),
    AUTH_FORBIDDEN_ACTION (HttpStatus.FORBIDDEN,  "AU1","권한이 없습니다."),
    AUTH_TOKEN_EXPIRED (HttpStatus.UNAUTHORIZED, "AU2", "토큰이 만료 되었습니다."),
    AUTH_INVALID_TOKEN (HttpStatus.UNAUTHORIZED, "AU3", "유효하지 않은 토큰입니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
