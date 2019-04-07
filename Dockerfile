FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY target/game*.jar game.jar
CMD java ${JAVA_OPTS} -jar game.jar