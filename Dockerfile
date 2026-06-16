FROM maven:3.8.6-eclipse-temurin-8 AS build
WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
COPY src src

RUN chmod +x mvnw && MAVEN_CONFIG= ./mvnw clean package -DskipTests

FROM eclipse-temurin:8-jre
WORKDIR /app

ENV JAVA_OPTS=""

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
