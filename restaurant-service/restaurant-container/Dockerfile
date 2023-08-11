FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*SNAPSHOT.jar restaurant-service.jar
ENTRYPOINT ["java","-jar","/restaurant-service.jar"]