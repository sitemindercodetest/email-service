FROM openjdk:8
VOLUME /tmp
ADD target/email-service-0.0.1-SNAPSHOT.jar email-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","email-service-0.0.1-SNAPSHOT.jar"]