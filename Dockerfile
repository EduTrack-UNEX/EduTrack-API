FROM maven:3.9.9-amazoncorretto-21 AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/edutrack.jar edutrack.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "edutrack.jar"]