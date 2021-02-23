FROM openjdk:14 as builder
ADD target/analytics-service.jar analytics-service.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","analytics-service.jar"]