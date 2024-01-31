# Stage 1: Build Maven project
# FROM maven:3.8.5-openjdk-17 AS build
# RUN mkdir -p /usr/src/app
# WORKDIR /usr/src/app
# COPY . /usr/src/app
# # RUN mvn package

# # Stage 2: Create the final image with the JAR file
# FROM eclipse-temurin:17-jdk
# RUN mkdir -p /usr/src/app
# WORKDIR /usr/src/app
# COPY --from=build /usr/src/app/target/dev-spotlight-0.0.1-SNAPSHOT.jar app.jar
# CMD ["java", "-jar", "app.jar"]

# select the image that it will be used
FROM maven:3.8.5-openjdk-17 AS build
# copy the entire code to the image
COPY . . 
# Run commands to install the application
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/dev-spotlight-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]