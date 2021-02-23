FROM openjdk:14 as builder
ADD target/analyticsclient-service.jar analyticsclient-service.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","analyticsclient-service.jar"]