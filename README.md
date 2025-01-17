# :computer: 프로젝트 소개

중고 상품을 등록하고 판매할 수 있는 중고거래 플랫폼입니다.  
사용자는 상품을 등록하고, **실시간 채팅**을 통해 판매자와 소통하며 상품을 구매할 수 있습니다.  
또한, Q&A 및 자유게시판을 통해 다양한 주제를 나눌 수 있는 **커뮤니티 기능**도 제공합니다.

---

## 📑 목차

1. [프로젝트 소개](#computer-프로젝트-소개)  
2. [개발 인원](#walking-개발-인원)  
3. [개발 기간](#watch-개발-기간)  
4. [UI](#art-ui)  
5. [기능](#mag-기능)  
6. [문제 해결 과정](#bulb-문제-해결-과정)  
    - [JPA N+1 문제 해결](#jpa-n1-문제-해결)  
    - [Proxy 객체를 활용해 쿼리 최적화](#proxy-객체를-활용해-쿼리-최적화)  
    - [로깅 프레임워크 최적화](#로깅-프레임워크-최적화)  
    - [비동기 처리 및 복합 인덱스 활용](#비동기-처리-및-복합-인덱스-활용)  
7. [개발 환경](#gear-개발-환경)  
8. [사용한 도구](#toolbox-사용한-도구)  

---

## :walking: 개발 인원

- **1인 프로젝트**

---

## :watch: 개발 기간

- **2024.08 ~ 2024.11** (총 3개월)

---

## :art: UI

### ▶️ 상품 리스트  
![상품](https://github.com/user-attachments/assets/7cdccde6-e4a9-49f7-a1bf-ca6f85fbe5ba)

### ▶️ 게시판  
![게시판](https://github.com/user-attachments/assets/2afb0838-44a8-4b92-bfab-668b0bd16f80)

### ▶️ 알림 기능  
![알림기능](https://github.com/user-attachments/assets/7aef671e-ae57-42fd-b3e8-e6f8fe0fbf96)

### ▶️ 채팅 기능  
![채팅기능](https://github.com/user-attachments/assets/1f030a36-d0ae-4d7a-928d-9c0bdbcaa10a)

### ▶️ 마이 페이지  
![마이페이지](https://github.com/user-attachments/assets/c51379ca-941c-41a8-9f5c-503a75834729)

### ▶️ 관리자 페이지  
![관리자페이지](https://github.com/user-attachments/assets/813c4909-55d1-4301-830c-7ea7fb317e21)

---

## :mag: 기능

### **상품 CRUD**  
- 상품 **생성, 조회, 수정, 삭제** 기능 제공.

### **채팅**  
- **WebSocket + STOMP 프로토콜**로 비동기 실시간 채팅 구현.  
- Spring Scheduler로 **일주일 지난 채팅 메시지** 자동 삭제.

### **알림**  
- **SSE 프로토콜**을 이용한 실시간 알림 구현.  
- [상세 설명](https://github.com/creamleeminsoo/UsedPark/wiki/%EC%95%8C%EB%9E%8C-%EA%B8%B0%EB%8A%A5%EA%B5%AC%ED%98%84).

### **마이페이지**  
- 등록한 상품, 게시글, 장바구니 조회.  
- 상품 상태 변경 (판매중, 판매 완료).  

### **관리자 페이지**  
- **회원, 상품, 게시글 관리 및 통계 분석**.  

### **검색 및 인증**  
- 상품/게시글/유저 검색.  
- **JWT 및 OAuth2**를 활용한 인증.  

---

## :bulb: 문제 해결 과정

### JPA N+1 문제 해결  
 - [Batch Size 를 통한 해결](https://github.com/creamleeminsoo/UsedPark/wiki/N-1%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0-(Batch-Size))

   
 - [Fetch Join 을 통한 해결](https://github.com/creamleeminsoo/UsedPark/wiki/N-1%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0(FetchJoin))

   
 - [OneToOne 관계 N+1 해결](https://github.com/creamleeminsoo/UsedPark/wiki/N-1%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0(One-To-One-%EA%B4%80%EA%B3%84))


### Proxy 객체를 활용한 쿼리 최적화  
- 채팅 이벤트의 쿼리 횟수를 **2회 → 1회로 감소**.  
[상세 설명 보기](https://github.com/creamleeminsoo/UsedPark/wiki/Proxy%EA%B0%9D%EC%B2%B4%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4-%EC%BF%BC%EB%A6%AC%ED%9A%9F%EC%88%98-%EC%A4%84%EC%9D%B4%EA%B8%B0)


### 로깅 프레임워크 최적화  
- 운영 환경에서 **Logback**을 활용해 로그 최적화.  
[상세 설명 보기](https://github.com/creamleeminsoo/UsedPark/wiki/%EB%A1%9C%EA%B9%85-%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4--%EB%A1%9C%EA%B7%B8-%EC%B5%9C%EC%A0%81%ED%99%94)



### 비동기 처리 및 복합 인덱스 활용  
- 좋아요 기능 최적화.  
[상세 설명 보기](https://github.com/creamleeminsoo/UsedPark/wiki/%EC%A2%8B%EC%95%84%EC%9A%94-%EA%B8%B0%EB%8A%A5-%EA%B0%9C%EC%84%A0(%EB%B9%84%EB%8F%99%EA%B8%B0,-%EB%B3%B5%ED%95%A9-%EC%9D%B8%EB%8D%B1%EC%8A%A4))



---

## :gear: 개발 환경

- **언어**: Java (JDK17), JavaScript  
- **프레임워크**: SpringBoot 3.2.0  
- **데이터베이스**: MySQL, H2  
- **템플릿 엔진**: Thymeleaf  

---

## :toolbox: 사용한 도구

- 인증: **JWT, OAuth2 (Google, Kakao)**  
- 성능 테스트: **JMeter**  
- 배포: **AWS (Elastic Beanstalk, RDS, S3)**  
- 채팅: **WebSocket, STOMP**  
- 알림: **SSE**  
- 로깅: **Slf4j, Logback**  
- CI/CD: **GitHub Actions**  
- 코드 관리: **Git**  
