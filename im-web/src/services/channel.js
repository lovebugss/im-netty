import {request} from 'umi';

/**
 *
 * @returns {Promise<void>}
 */
export const connectInit = async ({userId, channelId}) => {

  return request("/api/channel/connect", {
    method: 'GET',
    params: {
      userId, channelId
    },
  })
}
