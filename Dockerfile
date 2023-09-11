# Build Stage
FROM openjdk:20-jdk-slim
EXPOSE 8080
ADD target/security-0.0.1-SNAPSHOT.jar security-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/security-0.0.1-SNAPSHOT.jar"]
#WORKDIR /app
#COPY . /app/
#COPY . .
#RUN mvn clean package -Dskiptests
#
##Package stage
#
##From openjdk:17-alpine
#
#From openjdk:17-jdk-slim
#
##WORKDIR /app
#COPY --from=build /target/exam-0.0.1-SNAPSHOT.jar exam.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","exam.jar"]
