FROM eclipse-temurin:20-jdk-alpine
VOLUME /tmp
COPY target/*.jar springrest-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","springrest-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081