# 🐱 Chaeum Backend Repository
2025-Advanced-Capstone for BackEnd Repository [Chaeum]

---

## 📌 개요
Chaeum은 **기부의 새로운 패러다임을 제시하는 플랫폼**입니다.  
기존의 기부 시스템은 **빈곤 포르노** 방식(자극적인 연출을 통한 기부 유도)에 의존하는 경우가 많습니다.  
우리는 기부자와 수혜자가 모두 부담 없이 기부에 참여할 수 있도록 **게이미피케이션**을 적용한 **크라우드 펀딩 기반 기부 서비스**를 제공합니다.

---

## 🎯 Chaeum의 목표
1️⃣ 자극적인 콘텐츠 없이도 기부의 가치를 전달  
2️⃣ 게이미피케이션 요소를 통해 사용자 참여도를 높임  
3️⃣ 기부금 사용 내역의 투명한 공개를 통한 신뢰성 확보  
4️⃣ 수혜자가 원하는 물품을 직접 선택하여 기부가 이루어지도록 개선

---

## 🛠️ 주요 기능
| 🏷️ 카테고리 | 🛠️ 기능 설명 |
|------------|-------------|
| **회원** | 회원가입, 로그인, 소셜 로그인 (카카오, 네이버) |
| **기부** | 기부 내역 관리, 펀딩 시스템 |
| **포인트** | 기부 및 미션 수행 시 포인트 적립 |
| **친구** | 친구 추가, 친구 상태 관리 |
| **미션** | 일일 미션 및 보상 시스템 |
| **아이템** | 고양이 커스터마이징 아이템 |
| **출석** | 출석 체크 및 보상 지급 |
| **결제** | 기부 및 포인트 충전, 카카오페이 및 카드 결제 지원 |
| **칭호** | 기부 및 활동에 따른 칭호 부여 |

---

## 📂 프로젝트 구조
```plaintext
chaeum-api
 ├── .github/                   # GitHub 관련 설정
 ├── .gradle/                   # Gradle 빌드 관련 파일
 ├── .idea/                     # IntelliJ 프로젝트 설정 파일
 ├── build/                     # 빌드된 파일
 ├── gradle/                    # Gradle 래퍼 관련 파일
 ├── out/                       # 컴파일된 클래스 파일
 ├── src/
 │   ├── main/
 │   │   ├── java/com/chaeum/api/
 │   │   │   ├── domain/         # 도메인별 계층 구조
 │   │   │   │   ├── controller/ # API 컨트롤러
 │   │   │   │   ├── dto/        # 데이터 전송 객체
 │   │   │   │   ├── entity/     # JPA 엔티티
 │   │   │   │   ├── repository/ # 데이터베이스 인터페이스
 │   │   │   │   ├── service/    # 비즈니스 로직
 │   │   │   ├── global/         # 공통 모듈
 │   │   │   │   ├── config/     # 설정 파일 관리
 │   │   │   │   ├── entity/     # 공통 엔티티
 │   │   │   │   ├── exception/  # 예외 처리
 │   │   │   │   ├── handler/    # 예외 핸들러
 │   │   │   ├── ChaeumApiApplication.java
 │   │   ├── resources/
 │   │   │   ├── static/
 │   │   │   ├── templates/
 │   │   │   ├── application.yml.template
 │   ├── test/
 ├── .gitattributes
 ├── .gitignore
 ├── build.gradle
 ├── gradlew
 ├── gradlew.bat
 ├── HELP.md
 ├── README.md
 ├── settings.gradle
```

---

## 🖥️ 시스템 아키텍처 다이어그램

(추후 추가 예정)

---

## 🗂️ ERD (Entity Relationship Diagram)

(추후 추가 예정)

---

## 👥 기여자

| **Minsang22Kim** | **SongJaeHoonn** |
|:--:|:--:|
| ![Minsang](https://github.com/Minsang22Kim.png?size=100) | ![JaeHoon](https://github.com/SongJaeHoonn.png?size=100) |