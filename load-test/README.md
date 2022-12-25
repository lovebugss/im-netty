使用K6 进行压力测试

```shell
k6 run ./script.js
```

生成_pb.js文件

```shell
 protoc --js_out=import_style=commonjs,binary:. --proto_path im/im-common/src/main/proto/dto message.proto
```

参考:

[k6 websocket 支持protobuf](https://github.com/sparrowjack/k6_protobuff_demo)