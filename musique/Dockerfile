FROM openjdk:8-jdk-alpine

# Ajout d'un utilisateur non-root
RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

WORKDIR /app

COPY musique/target/*.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]
