FROM maven:3.9.8-amazoncorretto-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src



WORKDIR /app

EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
