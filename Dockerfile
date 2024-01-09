FROM maven:3-jdk-11

#VOLUME /tmp

COPY target/limited.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]