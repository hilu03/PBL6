# Stage 1: Build
FROM maven:3.9.8-amazoncorretto-17 AS build

# Set working directory and copy necessary files
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build the project with Maven
RUN mvn clean package -DskipTests

# Stage 2: Create image
# Use a smaller base image for better performance
FROM amazoncorretto:17.0.8-alpine

# Set working directory and copy the JAR from the build stage
WORKDIR /app
COPY --from=build /app/target/bookstore-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
