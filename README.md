# :computer:프로젝트 소개 

중고 상품을 등록하고 판매 할 수 있는 중고거래 플랫폼 입니다.

사용자는 상품을 등록하고, 채팅을 통해 판매자와 직접 소통하며 구매 할 수 있습니다.
또한, Q&A및 자유게시판을 통해 다양한 주제를 나눌 수 있는 커뮤니티 기능도 제공됩니다.


## 목차
1. [프로젝트 소개](#computer프로젝트-소개)
2. [개발인원](#walking-개발인원)
3. [개발기간](#watch-개발기간)
4. [기능](#mag-기능)
5. [문제 해결 과정](#문제-해결-과정)
    - [JPA N+1 문제 해결 과정](#jpa-n1-문제-해결-과정)
    - [Proxy 객체를 활용해 쿼리 횟수 줄이기](#proxy-객체를-활용해-쿼리-횟수-줄이기)
    - [로깅 프레임워크를 사용해 로그 최적화](#로깅-프레임워크를-사용해-로그-최적화)
    - [비동기 처리와 복합 인덱스를 활용한 최적화](#비동기-처리와-복합-인덱스를-활용한-최적화)
6. [개발 환경](#low_brightness-개발-환경)
7. [사용한 도구](#wrench-사용한-도구)



## :walking: 개발인원
1인 프로젝트

## :watch: 개발기간

2024.08 ~ 2024.11 (3달)

# UI

 ▶️ **상품 리스트**

 
![상품](https://github.com/user-attachments/assets/7cdccde6-e4a9-49f7-a1bf-ca6f85fbe5ba)


▶️ **게시판**

![게시판](https://github.com/user-attachments/assets/2afb0838-44a8-4b92-bfab-668b0bd16f80)


▶️ **알림 기능**

![알림기능](https://github.com/user-attachments/assets/7aef671e-ae57-42fd-b3e8-e6f8fe0fbf96)


▶️ **채팅 기능**

![채팅기능](https://github.com/user-attachments/assets/1f030a36-d0ae-4d7a-928d-9c0bdbcaa10a)


▶️ **마이 페이지**

![마이페이지](https://github.com/user-attachments/assets/c51379ca-941c-41a8-9f5c-503a75834729)


▶️ **관리자 페이지**

![관리자페이지 ](https://github.com/user-attachments/assets/813c4909-55d1-4301-830c-7ea7fb317e21)

▶️ **홈페이지**

![홈페이지 사진](https://github.com/user-attachments/assets/1ff63b61-56a6-463d-b75f-b2a6bd8fd9e5)


## :mag: 기능

**상품 CRUD** 
- 상품 생성, 읽기, 수정, 삭제 기능

**채팅**
- **WebSocket + STOMP 프로토콜**로 비동기 실시간 채팅 구현. Spring Scheduler로 일주일 지난 채팅 메시지의 DB 일괄 삭제 자동화.

**알림** 
- **SSE 프로토콜**을 이용한 비동기 실시간 알림 구현 -  [상세 설명](https://github.com/creamleeminsoo/UsedPark/wiki/%EC%95%8C%EB%9E%8C-%EA%B8%B0%EB%8A%A5%EA%B5%AC%ED%98%84)

**게시글 CRUD**
- 게시글 생성, 읽기, 수정, 삭제 기능

  
**마이페이지**
 - 내가 등록한 장바구니 조회

   
 - 내가 등록한 상품,게시글 조회

   
 - 내가 등록한 상품 상태 변경(판매중,판매 완료)

**관리자 페이지**
 - 상품,게시글,회원 통계 분석

   
 - 상품,게시글,회원 관리

   
 - 게시판, 카테고리 생성

**검색**
 - 상품/게시글/유저 검색

**인증**
 - JWT를 활용한 무상태 인증

   
 - OAUTH2 를 활용한 소셜 로그인

## 📘 문제 해결 과정

**JPA N+1 문제 해결 과정**


 - [Batch Size 를 통한 해결](https://github.com/creamleeminsoo/UsedPark/wiki/N-1%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0-(Batch-Size))

   
 - [Fetch Join 을 통한 해결](https://github.com/creamleeminsoo/UsedPark/wiki/N-1%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0(FetchJoin))

   
 - [OneToOne 관계 N+1 해결](https://github.com/creamleeminsoo/UsedPark/wiki/N-1%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0(One-To-One-%EA%B4%80%EA%B3%84))


**Proxy 객체를 활용해 쿼리 횟수 줄이기**
 - [채팅 이벤트 쿼리 횟수 2회에서 1회 감소](https://github.com/creamleeminsoo/UsedPark/wiki/Proxy%EA%B0%9D%EC%B2%B4%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4-%EC%BF%BC%EB%A6%AC%ED%9A%9F%EC%88%98-%EC%A4%84%EC%9D%B4%EA%B8%B0)



**로깅 프레임워크를 사용해 로그 최적화**
 - [운영 환경에서 로그 최적화](https://github.com/creamleeminsoo/UsedPark/wiki/%EB%A1%9C%EA%B9%85-%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4--%EB%A1%9C%EA%B7%B8-%EC%B5%9C%EC%A0%81%ED%99%94)



**비동기 처리와 복합 인덱스를 활용한 최적화**
 - [좋아요 기능 최적화](https://github.com/creamleeminsoo/UsedPark/wiki/%EC%A2%8B%EC%95%84%EC%9A%94-%EA%B8%B0%EB%8A%A5-%EA%B0%9C%EC%84%A0(%EB%B9%84%EB%8F%99%EA%B8%B0,-%EB%B3%B5%ED%95%A9-%EC%9D%B8%EB%8D%B1%EC%8A%A4))


## :low_brightness: 개발 환경


- `Java` (JDK17)
- `JavaScript`
- `SpringBoot 3.2.0`
- `HTML 5`
- `JPA`
- `MySql,H2`
- `Thymeleaf`

## :wrench: 사용한 도구

- 인증: `JWT`, `OAuth2 (Google, Kakao)`, `Spring Security`
- 성능 테스트: `JMeter`
- 코드 관리: `Git`
- CI/CD: `GitHub Actions`
- 배포: `AWS(ElasticBeanstalk,RDS,S3)`
- 채팅: `WebSocket`,`STOMP`
- 알람: `SSE`
- 로깅: `Slf4j`, `Logback`
- 유효성 검사: `Java Bean Validation`
- `Lombok`

