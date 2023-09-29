# Build Docker file off Line
#FROM openjdk:20-jdk-slim
#EXPOSE 8080
#ADD target/exam-docker.jar exam-docker.jar
#ENTRYPOINT ["java","-jar","/exam-docker.jar"]
#



# BUILD STAGE
FROM maven:3.9.4-amazoncorretto-20-debian AS Build
#WORKDIR /app
COPY . .
#COPY . /app/
RUN mvn clean package

#PACKAGE STAGE
From openjdk:20-jdk-slim
#WORKDIR /app
COPY --from=build /target/exam-docker.jar exam-docker.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","exam-docker.jar"]
