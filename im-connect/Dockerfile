
FROM openjdk:17-alpine
# https://github.com/grpc/grpc-java/issues/8751
RUN apk add gcompat
ENV LD_PRELOAD=/lib/libgcompat.so.0

COPY target/im-connect.jar im-connect.jar
CMD ["java","-XX:+PrintGCDetails","-XX:ErrorFile=/tmp/java-error%p.log", "-jar","/im-connect.jar"]