# :computer: 프로젝트 개요


중고거래 플랫폼 프로젝트


## 목차
- [프로젝트 소개](#gift-프로젝트-소개)
- [기능](#mag-기능)
- [ERD](#erd)
- [UI](#eye-ui)
- [개발 내용](#computer-개발-내용)
- [성능 최적화 및 문제점 해결](#scissors-성능-최적화-및-문제점-해결)
- [개발 환경](#low_brightness-개발-환경)
- [사용한 도구](#wrench-사용한-도구)




## :gift: 프로젝트 소개


이 프로젝트는 중고상품을 등록하고 판매할 수 있는 중고거래 플랫폼입니다. 

사용자는 상품을 등록하고, 채팅을 통해 판매자와 직접 소통하며 구매할 수 있습니다. 

또한, Q&A 및 자유게시판을 통해 다양한 주제를 나눌 수 있는 커뮤니티 기능도 제공됩니다.

알림 기능을 통해 게시글에 댓글이 달리거나, 

채팅 메시지 또는 좋아요를 받으면 즉시 알림을 받아볼 수 있습니다.

## :walking: 개발인원
1인 프로젝트

## :watch: 개발기간

2024.08 ~ 2024.10 (2달)


## :mag: 기능

- **상품 CRUD**


  - 상품 등록, 읽기, 수정, 삭제 기능 제공
  - 상품 등록시 최대 3개의 이미지와 함께 등록 가능하며, 대표이미지를 지정

<br>

- **채팅**


  - WebSocket + STOMP 프로토콜을 활용한 비동기 실시간 채팅
  - Spring Scheduler를 활용해 매일 오전1시, 일주일이 지난 채팅 메세지를 DB에서 일괄적으로 삭제구현(삭제 시 INFO 레벨 로그 자동 기록)

<br>

- **알람**


  - SSE 프로토콜을 이용한 비동기 실시간 알림 기능

<br>

- **게시판 CRUD**


  - 게시글 생성, 읽기, 수정, 삭제 기능 제공
  - 게시글 등록시 최대 3개의 이미지와 함께 등록 가능

<br>

- **마이페이지**


  - 회원 정보 수정 및 탈퇴
 
  
  - 장바구니 관리
 
  
  - 내가 쓴 글 및 내가 등록한 아이템 확인
 
  
  - 내가 등록한 상품 상태 변경 (판매 중, 판매 완료)

<br>

- **관리자 페이지**


  - **관리 도구**: 회원 수, 상품 수, 게시글 수 등의 지표 확인
 
  
  - **관리 기능**: 회원, 상품, 게시글 관리 (관리자가 모든 항목을 삭제할 수 있으며, 삭제 시 INFO 레벨 로그 자동 기록)

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
<a href="https://github.com/user-attachments/assets/a1f81cef-efce-42f4-8bc2-74d79ebe6848" target="_blank">
  <img src="https://github.com/user-attachments/assets/a1f81cef-efce-42f4-8bc2-74d79ebe6848" width="700">
</a>

> **채팅 화면**

<br>
<a href="https://github.com/user-attachments/assets/0d433884-f409-4e41-9ff8-8e949b7fb426" target="_blank">
  <img src="https://github.com/user-attachments/assets/0d433884-f409-4e41-9ff8-8e949b7fb426" width="400">
</a>

> **알람 화면**

<br>
<a href="https://github.com/user-attachments/assets/a59f3161-dd37-4bcd-9460-8ddab3c9b276" target="_blank">
  <img src="https://github.com/user-attachments/assets/a59f3161-dd37-4bcd-9460-8ddab3c9b276" width="700">
</a>

> **게시글 화면**


## :computer: 개발 내용

- [로깅 프레임워크를 사용해 로그 최적화](https://github.com/creamleeminsoo/UsedPark/wiki/%EB%A1%9C%EA%B9%85-%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4--%EB%A1%9C%EA%B7%B8-%EC%B5%9C%EC%A0%81%ED%99%94)
- [알람 기능 구현](https://github.com/creamleeminsoo/UsedPark/wiki/%EC%95%8C%EB%9E%8C-%EA%B8%B0%EB%8A%A5%EA%B5%AC%ED%98%84)


## :scissors: 성능 최적화 및 문제점 해결

현 프로젝트의 핵심 페이지인 상품 리스트 페이지에서 성능을 테스트하던 중, 20개의 상품을 조회하는 데 38개의 쿼리가 발생하는 비정상적인 동작을 발견했습니다. 
예상보다 많은 쿼리가 실행되면서 성능 저하를 유발했고,  원인을 조사한 결과 **N+1** 문제가 존재한다는 것을 확인했습니다.

또한 프로젝트 전반에 걸쳐 다수의 연관 관계를 사용하는 만큼, **N+1** 문제는 상품 리스트 페이지뿐만 아니라 모든 페이지에서 해결해야 할 중요한 과제라고 판단했습니다.

저는 **N+1**문제가 발생할수 있는 상황을 크게 3가지로 나눠 고민해봤습니다.

1, 즉시로딩(Eager Loading) 인 상황
2, 지연로딩(Lazy Loading) 인 상황
3, 일대일(OneToOne)관계일때 

**특히 즉시 로딩과 지연 로딩의 경우, 엔티티 간 연관 관계 설정에 따라 상황이 달라지므로, 세부 기준을 나누어 해결책을 마련했습니다.**

**1, 즉시로딩(Eager Loading) 인 상황** 
- ManyToOne (다대일) 관계   ->  `Fetch Join 사용하여 문제 해결`
- OneToMany(일대다) 관계
  </br>
       `1) Paging 적용 되어 있는 경우  ->  BatchSize , DTO로 맵핑 해서 문제해결`
        </br>`2) Paging 적용 안되어 있는 경우  ->  연관된 엔티티 하나만 FetchJoin 후 BatchSize를 통해 문제해결`
</br>

**2, 지연로딩(Lazy Loading) 인 상황**
- `BatchSize로 문제 해결`
  
</br>

**3, 일대일(OneToOne)관계 일떄**
- `양방향  ->  단방향으로 해결`

**문제 해결 과정**
- [N+1 문제 해결 (Batch Size)](https://github.com/creamleeminsoo/UsedPark/wiki/N-1%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0-(Batch-Size))
- [N+1 문제 해결 (Fetch Join)](https://github.com/creamleeminsoo/UsedPark/wiki/N-1%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0(FetchJoin))
- [N+1 문제 해결 (One-To-One 관계)](https://github.com/creamleeminsoo/UsedPark/wiki/N-1%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0(One-To-One-%EA%B4%80%EA%B3%84))
</br>

**성능 최적화 과정**
  
- [Proxy 객체를 사용해 쿼리횟수 줄이기](https://github.com/creamleeminsoo/UsedPark/wiki/Proxy%EA%B0%9D%EC%B2%B4%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4-%EC%BF%BC%EB%A6%AC%ED%9A%9F%EC%88%98-%EC%A4%84%EC%9D%B4%EA%B8%B0)




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
