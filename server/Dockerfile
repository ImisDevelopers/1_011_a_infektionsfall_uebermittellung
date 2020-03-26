FROM gradle:6.2.2-jdk11
ADD . /app
WORKDIR /app
RUN gradle build clean
ENTRYPOINT ["gradle", "bootrun"]
