FROM openjdk:17.0.2-slim-buster

LABEL maintainer="Fred"
LABEL version="1.0"

ENV DB_NAME="postgres"
ENV DB_HOSTNAME="localhost"
ENV DB_PORT="5432"
ENV DB_USERNAME="postgres"
ENV DB_PWD="postgres"

EXPOSE 8080

COPY maven/*.jar producer-api.jar

ENTRYPOINT [ "java" , "-Xms128m",  "-Xmx400m", "-jar", "-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector", "producer-api.jar", "--jdbc.url=jdbc:postgresql://${DB_HOSTNAME}:${DB_PORT}/${DB_NAME}", "--jdbc.password=${DB_PWD}", "--jdbc.username=${DB_USERNAME}"]