FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/user-management-service-0.0.1-SNAPSHOT.jar user-management-service.jar
EXPOSE 8094
ENTRYPOINT ["java","-jar","user-management-service.jar"]