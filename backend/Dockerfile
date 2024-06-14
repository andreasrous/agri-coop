FROM openjdk:21-rc-oracle
MAINTAINER andreasrous
WORKDIR /app
COPY target/agri-coop-0.0.1-SNAPSHOT.jar ./application.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "application.jar"]