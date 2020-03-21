FROM openjdk:8-jre-alpine
VOLUME /tmp
ADD build/libs/infektionsfall_uebermittellung-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java", "-Xmx64m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]