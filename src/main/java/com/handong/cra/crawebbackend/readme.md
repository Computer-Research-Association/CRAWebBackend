# 기본 구조 docs

## 1. DTO 분리

```text
client ---[Req....Dto]---> server

controller -> service 
    req-dto -> -dto 로 변환 (요청 DTO 에서 서비스 DTO 로 변환)

serivce -> controller 
    -dto -> res-dto (서비스 DTO 에서 응답 DTO 로 변환)

server ---[Res....Dto]---> client
```

서버로 요청 보낼때 DTO 를 분리해야 하는경우 위 방식으로 처리
만약 서비스내부 로직 필요 데이터와 요청, 응답 모두 데이터가 동일한 경우 단일 DTO로 처리

## 2. 기능 분리 (package)

```text
function
- controller 
    - FunctionController.class 
    - FunctionAdminController.class [optional]
- service
    - FunctionService.interface 
    - FunctionServiceImpl.class [구현]  
- repository
    - FunctionRepository.interface
- domain
    - Entity.class
- dto 
    -res
        - Res-Dto.class [response-dto]
    -req
         Req-Dto.class [Request-dto]
    - Dto.class [service-dto]
```

다양한 기능이 존재하기 때문에, 혼돈을 야기하고, 효율적으로 작업하기 위해 위 방식으로 패키지를 나눠서 작업 진행

## 3. 서버 통신 방식

```text

client[AZURE](react) -> DNS[CloudFlare](ssl) -> EC2[AWS] -> nginX[middleware] -> spring boot[java] -RDS[AWS]
                                                                                                   -S3[AWS]
```

추후 EC2 + RDS -> 동방 서버로 변경 예정

## 4. 보안 관련

Spring Security를 적용하여 보안 적용

### 필터 구현

1. [authentication](util/JwtAuthenticationFilter.java) : 인증 관련
2. [authorization](config/WebSecurityConfig.java) : 인가 관련
3. [userdetails](auth/domain/CustomUserDetails.java) : 인가 이후 유저 정보를 가져오는 객체

### 로그인 통신방식

```text

client -----------------------> server -----------------> DB
password -> AES 암호화            암호문 -> AES 복호화         단방향 암호화 하여 저장
```

### 토큰

1. 로그인시 리프레시 토큰 발급 -> DB 에 저장하여 비정상적인 접근 방지
2. 리이슈를 통해 엑세스 토큰 발급

엑세스 토큰을 헤더에 넣어 통신 진행.

## 5. DevOps

### 1. 모니터링

프로메테우스 -> 그라파나 (LOKI) 실시간 모니터링중

### 2. docker

모니터링 서버를 도커에 통합하여 EC2 배포하여 도커로 운영중

### 3. CI

main 브랜치에 merge 할 경우, 도커 compose 하여 서버에서 배포 자동진행
