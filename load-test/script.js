import ws from 'k6/ws';
import {check, crpyto} from 'k6';
import proto from "./packet_pb.js"

export const options = {
    stages: [
//    {duration: '30s', target: 100},
//    {duration: '60s', target: 1000},
//    {duration: '60s', target: 10},
    ],
    thresholds: {},
    vus: 20,
    duration: '60s'

}

export default () => {
    const url = 'ws://127.0.0.1:18080/ws?cid=ch_00001&uid=xxxxxxxxxx';

    const response = ws.connect(url, {}, function (socket) {
        socket.on('open', function open() {
            console.log('connected');
            var message_proto = new proto.Data()
            message_proto.setContent("你好-" + new Date().getTime())
            console.log(`send message: ${message_proto}`)
            socket.sendBinary(message_proto.serializeBinary().buffer);
            socket.setInterval(function timeout() {
                socket.ping();
                console.log('Pinging every 1sec (setInterval test)');
            }, 30000);
        });

        socket.on('ping', () => console.log('PING!'));
        socket.on('pong', () => console.log('PONG!'));
        socket.on('message', function (message) {
            console.log(`Received message: ${message}`);
        });
        socket.on('binaryMessage', function (message) {
               console.log(`Received binaryMessage: ${proto.Data.deserializeBinary(message)}`);
            });
        socket.on('close', () => console.log('disconnected'));

        socket.on('error', (e) => {
            if (e.error() != 'websocket: close sent') {
                console.log('An unexpected error occurred: ', e.error());
            }
        });

    });

    check(response, {'status is 101': (r) => r && r.status === 101});
}
