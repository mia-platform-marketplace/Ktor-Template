FROM openjdk:8-jdk-alpine

ARG COMMIT_SHA=<not-specified>
ARG BUILD_FILE_NAME=application.jar

WORKDIR /home/java/app

RUN adduser -D -S -s /sbin/nologin -G root java && \
    echo "mia_template_service_name_placeholder: $COMMIT_SHA" >> ./commit.sha
COPY build/libs/${BUILD_FILE_NAME} ./application.jar
COPY src/main/resources/application.conf ./application.conf
COPY logback.xml ./logback.xml
ENV LOG_CONFIG_FILE /home/java/app/logback.xml
COPY LICENSE ./LICENSE
USER java

CMD ["java","-jar", "./application.jar", "-config=application.conf"]