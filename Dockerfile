# ================================
# Stage 1: Build the application
# ================================
FROM maven:3.9.12-eclipse-temurin-17 AS builder

WORKDIR /app

# Cache dependencies first (faster builds)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests


# ================================
# Stage 2: Runtime image
# ================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy fat/executable jar
COPY --from=builder /app/target/*.jar app.jar

# Expose application port
EXPOSE 8081

# Healthcheck (used by Docker & Jenkins)
HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# Run Spring Boot
ENTRYPOINT ["java","-jar","app.jar"]
