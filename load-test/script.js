import ws from 'k6/ws';
import http from 'k6/http';
import { check, crpyto } from 'k6';
import proto from "./message_pb.js"

export const options = {
    stages: [
        //    {duration: '30s', target: 100},
        //    {duration: '60s', target: 1000},
        //    {duration: '60s', target: 10},
    ],
    thresholds: {},
    vus: 1,
    duration: '30s'

}
const channelId = "ch_0000001";
const userId = "user_123";

export function setup() {
    const payload = JSON.stringify({
        "userId": userId,
        "channelId": channelId
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };
    const res = http.post('http://localhost:8580/api/chat/init', payload, params);
    return res.json();
}

export default (data) => {
    let token = data.data.token;
    let time = data.data.time;
    let nodeInfo = data.data.nodeInfo[0];
    const url = `${nodeInfo.protocol}://${nodeInfo.address}:${nodeInfo.port}/ws?cid=${channelId}&uid=${userId}&t=${token}&tm=${time}`;
    console.log("url", url)
    const response = ws.connect(url, {}, function (socket) {
        socket.on('open', function open() {
            console.log('connected');
            var message_proto = new proto.Packet()
            var message = new proto.Message();
            message.setContent("你好-" + new Date().getTime());
            message_proto.setDatatype(1);
            message_proto.setMessage(message);
            message_proto.setTimestamp(new Date().getTime())
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
            console.log(`Received binaryMessage: ${proto.Packet.deserializeBinary(message)}`);
        });
        socket.on('close', () => console.log('disconnected'));

        socket.on('error', (e) => {
            if (e.error() != 'websocket: close sent') {
                console.log('An unexpected error occurred: ', e.error());
            }
        });

    });

    check(response, { 'status is 101': (r) => r && r.status === 101 });
}
