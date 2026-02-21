# --------- Build Stage ----------
FROM gradle:8-jdk21-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle gradle.* ./
COPY src ./src

RUN gradle clean build -x test --no-daemon

# --------- Run Stage ----------

# Use lightweight JDK image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy jar file
COPY build/libs/*.jar app.jar

# Expose application port
EXPOSE 5477

# Run application
ENTRYPOINT ["java","-jar","app.jar"]