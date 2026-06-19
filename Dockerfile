# Use Maven + Java 21 image for building the application
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy all project files into the container
COPY . .

# Build the JAR file while skipping tests
RUN mvn clean package -DskipTests

# Use lightweight Java 21 runtime image for production
FROM eclipse-temurin:21-jre

# Set working directory inside the runtime container
WORKDIR /app

# Copy the generated JAR from the build stage and rename it to app.jar
COPY --from=build /app/target/*.jar app.jar

# Inform Docker that the application listens on port 8080
EXPOSE 8080

# Start the Spring Boot application when the container runs
ENTRYPOINT ["java", "-jar", "app.jar"]