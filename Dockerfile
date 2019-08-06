FROM openjdk:8-jdk-alpine
MAINTAINER Erik Levi <levi.erik@gmail.com>
ADD target/dataexplorer-0.0.1-SNAPSHOT.jar data-explorer.jar
ENTRYPOINT ["java", "-jar", "/data-explorer.jar"]
