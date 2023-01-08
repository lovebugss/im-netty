import React, {useEffect, useState} from 'react';
import WebSocket from 'isomorphic-ws';
import protobuf from 'protobufjs';
import {useLocation} from 'umi'

async function loadProtobuf() {
  const root = await protobuf.load('message.proto');
  const Message = root.lookupType('Message');
  const Packet = root.lookupType('Packet');
  const Event = root.lookupType('Event');

  return {Packet, Message, Event};
}

const ChatRoom = (props) => {
  const [messages, setMessages] = useState([]);
  const [onlineUser, setOnlineUser] = useState([]);
  const [ws, setWs] = useState(null);
  const [input, setInput] = useState('');
  const location = useLocation();
  const {channelId, uid, token, nodeInfo, time} = location.query

  const [Message, setMessage] = useState(null);
  const [Packet, setPacket] = useState(null);
  const [Event, setEvent] = useState(null);

  useEffect(() => {
    async function init() {
      const {Packet, Message, Event} = await loadProtobuf();
      setMessage(Message);
      setPacket(Packet);
      setEvent(Event);
    }

    init();
  }, []);

  useEffect(() => {
    if (!Message || !Packet || !Event) {
      return;
    }
    let url = `ws://192.168.0.108:18081/ws?cid=${channelId}&uid=${uid}&t=${token}&tm=${time}`
    const ws = new WebSocket(url);
    setWs(ws);
    ws.onopen = function open() {
      // setTimeout(function timeout() {
      //   ws.send("ping");
      // }, 3000);
    };
    ws.addEventListener("pong", () => {
      console.log("pong")
    })
    ws.onmessage = (event) => {
      let reader = new FileReader();
      reader.readAsArrayBuffer(event.data);
      reader.onload = () => {
        let arrayBuffer = reader.result;
        let buffer = new Uint8Array(arrayBuffer);

        let packet = Packet.decode(buffer);
        let message = Message.decode(packet.data);
        console.log(message)
        setMessages((prevMessages) => {
            let len = prevMessages.length
            if (len > 50) {
              prevMessages = prevMessages.slice(0, 50)
            }
            return [...prevMessages, message]
          }
        );
      };
    };

    return () => {
      ws.close();
    };
  }, [Message, Packet, Event]);

  function handleSend() {
    if (!ws || !input || !Message || !Packet || !Event) {
      return;
    }

    const message = Message.create({content: input, type: 0});
    const data = Message.encode(message).finish();
    ws.send(data);
    setInput('');
  }

  function handleClose() {
    if (!ws) {
      return;
    }

    ws.close();
    setWs(null);
  }

  return (
    <div>
      <ul>
        {messages.map((message, index) => (
          <li key={message.messageId}>{`${message.userId}: ${message.content}`}</li>
        ))}
      </ul>
      <input value={input} onChange={(event) => setInput(event.target.value)}/>
      <button onClick={handleSend}>Send</button>
      <button onClick={handleClose}>Close WebSocket</button>
    </div>
  );
}
export default ChatRoom
