FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java","-jar","/order-service.jar"]