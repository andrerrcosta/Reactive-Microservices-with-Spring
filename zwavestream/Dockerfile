FROM openjdk:14 as builder
ADD target/wavestream-service.jar wavestream-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","wavestream-service.jar"]