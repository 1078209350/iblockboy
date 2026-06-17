FROM maven:3.8.6-eclipse-temurin-8 AS build
WORKDIR /app

# 先复制 pom.xml，单独下载依赖（这层可以缓存！）
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 再复制源代码
COPY src src

RUN mvn clean package -DskipTests -B

FROM eclipse-temurin:8-jre
WORKDIR /app

ENV JAVA_OPTS=""

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
