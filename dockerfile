FROM openjdk:17

WORKDIR /app

COPY ./target/mockie-0.0.1-SNAPSHOT.jar /app

EXPOSE 32615

CMD ["java", "-jar", "mockie-0.0.1-SNAPSHOT.jar"]
