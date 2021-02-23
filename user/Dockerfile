FROM openjdk:14 as builder
ADD target/user-service-snapshot.jar user-service-snapshot.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","user-service-snapshot.jar"]