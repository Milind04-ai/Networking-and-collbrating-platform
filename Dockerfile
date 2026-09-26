# =========================
# Build stage
# =========================
FROM maven:3.9.13-eclipse-temurin-8 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


# =========================
# Runtime stage
# =========================
FROM tomcat:9.0-jdk8-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/Nexus-1.0-SNAPSHOT.war \
    /usr/local/tomcat/webapps/Nexus.war

EXPOSE 10000

CMD ["sh", "-c", "PORT=${PORT:-8080}; sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT}\\\"/\" /usr/local/tomcat/conf/server.xml; catalina.sh run"]