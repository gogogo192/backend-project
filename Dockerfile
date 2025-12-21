# 21 JDK Alpine 기반
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp

# Bash와 netcat 설치 (wait-for-it.sh 실행용)
RUN apk add --no-cache bash netcat-openbsd

# 애플리케이션 JAR 복사
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar

# wait-for-it.sh 복사 후 실행 권한 부여
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# 컨테이너 시작 시 MySQL 준비 후 앱 실행
ENTRYPOINT ["/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "/app.jar"]
