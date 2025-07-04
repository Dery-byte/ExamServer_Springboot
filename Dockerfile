## Build Docker file off Line
##FROM openjdk:20-jdk-slim
##EXPOSE 8080
##ADD target/exam-docker.jar exam-docker.jar
##ENTRYPOINT ["java","-jar","/exam-docker.jar"]
##
#
#
## BUILD STAGE
#FROM maven:3.9.4-amazoncorretto-20-debian AS build
##WORKDIR /app
#COPY . .
##COPY . /app/
##RUN mvn clean package
#RUN mvn clean package -DskipTests
#
##PACKAGE STAGE
#FROM openjdk:20-jdk-slim
##WORKDIR /app
#COPY --from=build /target/exam-docker.jar exam-docker.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","exam-docker.jar"]

## BUILD STAGE
#FROM maven:3.9.4-amazoncorretto-20-debian AS build
##WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
## PACKAGE STAGE
#FROM openjdk:20-jdk-slim
#COPY --from=build /target/exam-docker.jar exam-docker.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "exam-docker.jar"]


# BUILD STAGE
#FROM maven:3.9.4-amazoncorretto-20-debian AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests
#
## Package Stage
#FROM openjdk:20-jdk-slim
#WORKDIR /app
#COPY --from=build /app/target/*.jar exam-docker.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "exam-docker.jar"]



# BUILD STAGE
FROM maven:3.9.4-amazoncorretto-20-debian AS build
WORKDIR /app
#COPY pom.xml .
COPY . .
RUN mvn clean package -DskipTests

# RUNTIME STAGE
FROM openjdk:20-jdk-slim
WORKDIR /app
COPY --from=build /app/target/exam-docker.jar exam-docker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "exam-docker.jar"]