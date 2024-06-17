# 포도알레나 🎟️🍇
 콘서트 티켓 예매, MD샵 상품 구매
 
## 팀 구성원 📋

| 이름   | 역할            | 이메일                   | 깃허브 |
|--------|-----------------|--------------------------|----------------|
| 원상운 | 프로젝트 매니저 | wsu063@gmail.com      | https://github.com/wsu063  |
| 박은화 | 프론트, 백엔드 | cu_oo@naver.com     | https://github.com/poelri |
| 오승찬 | 프론트, 백엔드 | younghee@example.com     | https://github.com/sseung519 |
| 이정민 | 프론트, 백엔드 | exo1461@naver.com | https://github.com/Jjeongmean |

## 프로젝트 정보 📃
- **개발 기간:** 2024.05.23 ~ 2024.06.18

## 프로젝트 목표 🎯

우리의 주요 목표는 콘서트 티켓 예약과 함께 팬들이 좋아하는 아티스트와 이벤트와 관련된 다양한 상품을 손쉽게 구매할 수 있도록 하는 것입니다. 사용자 친화적인 인터페이스와 강력한 백엔드 지원을 통해 포괄적이고 안전한 쇼핑 경험을 보장합니다.

## 사용된 기술 📚

### 백엔드 

- **Java:** 어노테이션 사용
- **Spring Framework:** 의존성 주입, 웹 요청 처리, 데이터 접근 계층 관리
- **Spring Data JPA:** 데이터 접근 계층 구현을 용이하게 함
- **Spring Boot:** 독립 실행형 애플리케이션을 쉽게 만들기 위한 프레임워크
- **Spring Security:** 비밀번호 인코딩 및 접근 제한
- **Hibernate:** JPA 구현체로서 ORM을 통해 데이터베이스와 객체 간의 매핑을 담당
- **MySQL Driver:** 데이터베이스 연동

### 프론트엔드

- **Thymeleaf:** 서버사이드 템플릿 엔진
- **Bootstrap:** CSS 프레임워크
- **JavaScript 및 jQuery:** 클라이언트 사이드 스크립팅 언어 및 DOM 조작 및 AJAX 요청에 사용
- **HTML 및 CSS:** 웹 페이지 마크업 및 스타일링

### 보안

- **CSRF Protection:** CSRF 토큰을 통한 보안 강화

## ERD
<details>

<summary>ERD</summary>

![GrapeArena Logo](./GRAPEARENA_ERD.png)

</details>

## 주요 기능 ✨

- **콘서트 티켓 예매:** 콘서트 티켓을 예매 후 DB에 저장합니다.
- **MD 샵:** 좋아하는 아티스트와 이벤트 관련 상품을 탐색하고 구매할 수 있습니다.
- **안전한 거래:** CSRF토큰을 이용 해 보안을 위해 한번 더 체크.
- **반응형 디자인:** 데스크톱 및 모바일 장치에서 최적화되어 사용자 경험을 최대화합니다.


