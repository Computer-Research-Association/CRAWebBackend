# COMMON

> 기본 기능 도메인

1. 기본 controller : root path 접근시 HTTP200 전송
2. BaseEntity : 기본 엔티티
    1. deleted true 처리로 데이터를 삭제처리 하므로, jpa 작성시 주의.
    2. 단, 유저 삭제는 DB 삭제를 진행하며, deleted 가 true 인 경우 휴면 계정 이라는 뜻