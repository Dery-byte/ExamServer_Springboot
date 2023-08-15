# Build Stage
FROM maven:3.8.3-openjdk-17 AS build
#WORKDIR /app
#COPY . /app/
COPY . .
RUN mvn clean package -Dskiptests

#Package stage

#From openjdk:17-alpine

From openjdk:17-jdk-slim

#WORKDIR /app
COPY --from=build /target/exam-0.0.1-SNAPSHOT.jar exam.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","exam.jar"]
