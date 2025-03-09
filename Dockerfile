# 1. 멀티 스테이지 빌드 사용
# 빌드 단계
FROM gradle:8.10.1-jdk21 AS build

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 파일을 복사하고 종속성 다운로드
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build --no-daemon  # Gradle 빌드 실행

# 2. 프로덕션 이미지를 생성하는 단계
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일을 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션이 사용하는 포트 노출
EXPOSE 3000

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
