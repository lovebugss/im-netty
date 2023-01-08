import {Redirect} from 'umi'

const useAuth = (channelId, nodeInfo, time, token) => {
  let currTime = new Date().getTime() / 1000;
  let isLogin = !!channelId && !!nodeInfo && !!time && !!token && (currTime - time < 300);
  return {
    isLogin
  }
}

export default (props) => {
  let {channelId, nodeInfo, time, token} = props.location.query
  const {isLogin} = useAuth(channelId, nodeInfo, time, token);
  if (isLogin) {
    return <div>{props.children}</div>;
  } else {
    return <Redirect to="/"/>;
  }
}
