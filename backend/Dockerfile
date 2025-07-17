# Java 21버전.
FROM openjdk:21-jdk

# 컨테이너 내부 사용 디렉토리 지정.
WORKDIR /devTogether

# 로컬에서 빌드된 Jar 파일을 컨테이너로 복사.
COPY build/libs/*.jar devTogether.jar

# 컨테이너 시작시 실행 명령어 지정.
ENTRYPOINT ["java", "-jar", "devTogether.jar"]