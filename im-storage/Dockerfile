

FROM openjdk:17-alpine
# https://github.com/grpc/grpc-java/issues/8751
RUN apk add gcompat
ENV LD_PRELOAD=/lib/libgcompat.so.0

COPY target/im-storage.jar im-storage.jar
ENTRYPOINT ["java","-jar","/im-storage.jar"]