FROM gradle:6.2.2-jdk11
ADD . /app
WORKDIR /app
RUN gradle -x :client:build build clean --no-daemon
ENTRYPOINT ["gradle", "bootrun"]
