FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*SNAPSHOT.jar payment-service.jar
ENTRYPOINT ["java","-jar","/payment-service.jar"]