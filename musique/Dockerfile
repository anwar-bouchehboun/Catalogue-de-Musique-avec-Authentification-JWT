FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY musique/target/*.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]
