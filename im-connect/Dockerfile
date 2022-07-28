

FROM openjdk:17-alpine

COPY target/im-connect.jar im-connect.jar
RUN ulimit -c unlimited
ENTRYPOINT ["java","-jar","/im-connect.jar"]