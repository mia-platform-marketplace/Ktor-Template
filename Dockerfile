FROM openjdk:11-jdk

ARG COMMIT_SHA=<not-specified>
ARG BUILD_FILE_NAME=application.jar

WORKDIR /home/java

RUN adduser --disabled-password --shell /sbin/nologin --ingroup root java
RUN mkdir app
RUN echo "mia_template_service_name_placeholder: $COMMIT_SHA" >> ./app/commit.sha
COPY build/libs/${BUILD_FILE_NAME} ./application.jar
COPY src/main/resources/application.conf ./application.conf
COPY logback.xml ./logback.xml
COPY LICENSE ./LICENSE
USER java

CMD ["java","-jar", "./application.jar", "-config=application.conf"]
