# 블로그 검색 서비스

## 프로젝트 환경

* Java 17
* Gradle 7.6.1
* Spring Boot 3.0.4

## 라이브러리

- spring-retry - 여러 대의 서버로 분산 처리시 발생할 수 있는 키 중복 오류, 낙관적 락 오류 발생시 재시도
- openfeign - 어노테이션 기반으로 외부 api 호출
- caffeine - 로컬 캐시 클라이언트
- okhttp - openfeign에서 connection pool 설정을 위해 사용
- resilience4j - Circuit Breaker, Retry 기능
- guava - AtomicLongMap 사용
- springdoc - API 문서 생성 자동화

## API 명세
- http://localhost:8080/swagger-ui/index.html