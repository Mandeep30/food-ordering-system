FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*SNAPSHOT.jar customer-service.jar
ENTRYPOINT ["java","-jar","/customer-service.jar"]