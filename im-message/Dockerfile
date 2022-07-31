

FROM openjdk:17-alpine
# https://github.com/grpc/grpc-java/issues/8751
RUN apk add gcompat
ENV LD_PRELOAD=/lib/libgcompat.so.0

COPY target/im-message.jar im-message.jar
ENTRYPOINT ["java","-jar","/im-message.jar"]