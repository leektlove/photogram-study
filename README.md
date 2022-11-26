
# [Chapter 12] 검수 완성 ~
# [Chapter 11] 유효성 검사
# [Chapter 10] 댓글 구현하기 2022-11-24 코멘트 달기
# [Chapter 9] 기타 기능 구현 2022-11-24 프로필 사진 변경
# [Chapter 8] 좋아요 구현하기 2022-11-23 완료 무한 참조 잡기 Like 
# [Chapter 7] 스토리 페이지  2022-11-23 완료 구독정보 뷰 렌더링 profile.js .jsp 수정하기
# [Chapter 6] 구독정보 뷰 렌더링  완료 2022-11-22
# [Chapter 5] 프로필 페이지 완료 2022-11-22


# 포토그램 - 인스타그램 클론 코딩

### STS 툴 버그가 발견되었습니다.
- 아래 주소로 가서 4.0.6 버전으로 설치해주세요. 아니면 의존성 다운로드 79프로에서 무한루프가 발생합니다.
- https://github.com/spring-projects/sts4/wiki/Previous-Versions

### STS 툴에 세팅하기 - 플러그인 설정
- https://blog.naver.com/getinthere/222322821611

### 의존성

- Sring Boot DevTools
- Lombok
- Spring Data JPA
- MariaDB Driver
- Spring Security
- Spring Web
- oauth2-client

xml
<!-- 시큐리티 태그 라이브러리 -->
## <dependency>
	<groupId>org.springframework.security</groupId>
	<artifactId>spring-security-taglibs</artifactId>
## </dependency>

<!-- JSP 템플릿 엔진 -->
## <dependency>
	<groupId>org.apache.tomcat</groupId>
	<artifactId>tomcat-jasper</artifactId>
	<version>9.0.43</version>
## </dependency>

<!-- JSTL -->
## <dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
## </dependency>


### 데이터베이스

sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database photogram;


### yml 설정

yml
server:
  port: 8080
  servlet:
    context-path: /
    encoding:
      charset: utf-8
      enabled: true
    
spring:
  mvc:
    view:
      prefix: /WEB-INF/views/
      suffix: .jsp
      
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/cos?serverTimezone=Asia/Seoul
    username: cos
    password: cos1234
    
  jpa:
    open-in-view: true
    hibernate:
      ddl-auto: update
      naming:
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    show-sql: true
      
  servlet:
    multipart:
      enabled: true
      max-file-size: 2MB

  security:
    user:
      name: test
      password: 1234   

file:
  path: C:/src/springbootwork-sts/upload/


### 태그라이브러리

jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

