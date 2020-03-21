FROM gradle:6.0.1-jdk11
ADD . /app
WORKDIR /app
RUN gradle build clean --debug
ENTRYPOINT ["gradle","bootrun"]
