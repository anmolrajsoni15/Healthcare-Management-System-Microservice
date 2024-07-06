# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project's pom.xml and source code into the container
COPY pom.xml /app
COPY src /app/src

# Package the application using Maven
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

# Expose the port the application runs on
EXPOSE 8762

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "target/service_registory-0.0.1-SNAPSHOT.jar"]
