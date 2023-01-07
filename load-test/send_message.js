import http from 'k6/http';
import { check, sleep } from 'k6';
export const options = {
  vus: 100,
  duration: '30s',
};

const host = 'http://localhost:8580';
const channelId = "ch_00000001";
const userId = "user_123";
export default function () {
  const url = host + '/message/send';
  const payload = JSON.stringify({
                                     "to": channelId,
                                     "from": userId,
                                     "type": 1,
                                     "message": {
                                         "type": 1,
                                         "content": "123"
                                     }
                                 });

  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const res = http.post(url, payload, params);
  console.log(JSON.stringify(res.body));
  check(res, { 'status was 200': (r) => r.status == 200 });
  sleep(1);
}
