FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY ./target/*.jar app.jar

EXPOSE 8086

CMD ["java", "-jar", "/app/app.jar"]
