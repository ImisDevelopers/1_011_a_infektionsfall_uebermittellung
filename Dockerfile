FROM gradle:6.2.2-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11-jre-slim

VOLUME /tmp
ADD build/libs/ims.jar app.jar
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Dspring.environment=production"
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java", "-Xmx64m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]