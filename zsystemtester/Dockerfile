FROM openjdk:14 as builder
ADD target/systemtester-service.jar systemtester-service.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","systemtester-service.jar"]