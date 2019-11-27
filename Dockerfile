FROM java:8-jdk-alpine

COPY ./target/wss-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "wss-0.0.1-SNAPSHOT.jar"]