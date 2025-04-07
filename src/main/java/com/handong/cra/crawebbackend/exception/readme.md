# EXCEPTION

> 각종 에러처리

## 서버 기본 에러 처리

HTTP 500 에러 발생 세분화
같은 에러 발생시 판별 가능하게 처리 하기 위해 에러 메시지, 에러 코드 전송
EX) 404 에러 발생시 USER NOTFOUND 인지 BOARD NOT FOUND 인지?

### 올바르지 못한 HTTP 코드 전송시 처리 방법

[CraExceptionHandler](./CraExceptionHandler.java)  클래스에서 해당 익셉션 작성

```java

@ExceptionHandler(Exception.class) // 발생한 익셉션의 클래스
public ResponseEntity<ErrorResponse> commonException(Exception e) {
    log.error("CommonException", e); // 에러 발생 로그 작성 
    return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR); // http status
}

```

## 익셉션 작성 방법

### 1. [ErrorCode](./ErrorCode.java)  클래스에서 익셉션 작성

```java
// 모두 대문자로 작성할것
DOMAINNAME_ERROR_TEXT(/* http error code*/, /*Error code*/, /*Error message*/)
```

### 2. 도메인 폴더 내에 새로운 익셉션 클래스 생성

exception 도메인 내부에 있는 도메인 폴더에 익셉션 클래스 작성

```java
public class DomainName-ErrorType-

Exception {
    public DomainName -ErrorType - Exception() { // no arg constructor
        super(ErrorCode.DOMAINNAME_ERROR_TEXT); // 2번에서 작성한 익셉션
    }
} 
```

### 3. 해당 익셉션 사용

```java
throw new DomainName-ErrorType-

Exception();
```