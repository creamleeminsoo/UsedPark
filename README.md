# :computer: 프로젝트 개요


중고거래 플랫폼 프로젝트


## 목차
- [프로젝트 소개](#gift-프로젝트-소개)
- [기능](#mag-기능)
- [ERD](#erd)
- [UI](#eye-ui)
- [개발 환경](#low_brightness-개발-환경)
- [사용한 도구](#wrench-사용한-도구)




## :gift: 프로젝트 소개


이 프로젝트는 중고상품을 등록하고 판매할 수 있는 중고거래 플랫폼입니다. 

사용자는 상품을 등록하고, 채팅을 통해 판매자와 직접 소통하며 구매할 수 있습니다. 

또한, Q&A 및 자유게시판을 통해 다양한 주제를 나눌 수 있는 커뮤니티 기능도 제공됩니다.

알림 기능을 통해 게시글에 댓글이 달리거나, 

채팅 메시지 또는 좋아요를 받으면 즉시 알림을 받아볼 수 있습니다.


## :mag: 기능

- **상품 CRUD**


  - 상품 등록, 읽기, 수정, 삭제 기능 제공

<br>

- **채팅**


  - WebSocket + STOMP 프로토콜을 활용한 실시간 채팅

<br>

- **알람**


  - SSE 프로토콜을 이용한 실시간 알림 기능

<br>

- **게시판 CRUD**


  - 게시글 생성, 읽기, 수정, 삭제 기능 제공

<br>

- **마이페이지**


  - 회원 정보 수정 및 탈퇴
 
  
  - 장바구니 관리
 
  
  - 내가 쓴 글 및 내가 등록한 아이템 확인
 
  
  - 상품 상태 변경 (판매 중, 판매 완료)

<br>

- **관리자 페이지**


  - **관리 도구**: 회원 수, 상품 수, 게시글 수 등의 지표 확인
 
  
  - **관리 기능**: 회원, 상품, 게시글 관리

<br>

- **검색**


  - 상품, 게시글, 회원 등 검색 기능 제공

<br>

- **페이징**


  - 상품 및 게시글 목록을 페이지 단위로 나누어 표시

<br>

- **정렬**


  - **상품**: 최신순, 등록순, 카테고리, 주소 등으로 정렬 가능
 
    
  - **게시글**: 최신순, 등록순, 좋아요 순 정렬 가능

<br>

- **인증**


  - JWT 기반 인증
 
  
  - OAuth2를 이용한 구글, 카카오 소셜 로그인 지원

## ERD

![최종 ERD](https://github.com/user-attachments/assets/910bdb34-5f2e-420b-a10a-2a5b04a42a5c)

## :eye: UI



<a href="https://github.com/user-attachments/assets/1c6d976c-5dba-42a4-89a1-cabecd186946" target="_blank">
  <img src="https://github.com/user-attachments/assets/1c6d976c-5dba-42a4-89a1-cabecd186946" width="700">
</a>


> **상품 리스트 화면**

<br>
<img src="https://github.com/user-attachments/assets/a1f81cef-efce-42f4-8bc2-74d79ebe6848" width="700">

> **채팅 화면**

<br>
<img src="https://github.com/user-attachments/assets/0d433884-f409-4e41-9ff8-8e949b7fb426" width="400">

> **알람 화면**

<br>
<img src="https://github.com/user-attachments/assets/a59f3161-dd37-4bcd-9460-8ddab3c9b276" width="700">

> **게시글 화면**



## :low_brightness: 개발 환경
- `Java (JDK 17)`
- `JavaScript`
- `HTML 5`
- **프레임워크**: `SpringBoot 3.2.0`
- **템플릿 엔진**: `Thymeleaf`
- **ORM**: `JPA`
- **DB**
  - 배포: `MySql`
  - 테스트: `H2`
- **Server**
  - WebServer `Nginx`
  - WAS: `Tomcat`
- **빌드 도구**: `Gradle`

  
## :wrench: 사용한 도구
- **인증**: `JWT`, `OAuth2 (Google, Kakao)`, `Spring Security`
- **성능 테스트**: `JMeter`
- **형상 관리**: `Git`
- **CI/CD**: `GitHub Actions`
- **배포**: `AWS(Elastic Beanstalk,RDS,S3)`
- **채팅**: `WebSocket` , `STOMP`
- **알람**: `SSE`
- **로깅**: `Slf4j`, `Logback`
- **유효성 검사**: `Java Bean Validation`
- `Lombok`
