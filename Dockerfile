# Stage 1: build
# Start with a Maven image that includes JDK 17
FROM maven:3.9.8-amazoncorretto-17 AS build

# Copy source code and pom.xml file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build source code with maven
RUN mvn clean package -DskipTests

# Stage 2: create image
# Start with Amazon Correto JDK 17
FROM amazoncorretto:17.0.8

# Set working folder to /app and copy the compiled file from above step
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
