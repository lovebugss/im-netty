package com.itrjp.im.api.controller;

import com.itrjp.common.result.Result;
import com.itrjp.common.service.TokenService;
import com.itrjp.im.api.entity.Channel;
import com.itrjp.im.api.entity.ChannelConnectInfo;
import com.itrjp.im.api.entity.ChannelNodeInfo;
import com.itrjp.im.api.entity.InitParam;
import com.itrjp.im.api.pojo.param.CreateChannelParam;
import com.itrjp.im.api.service.ChannelService;
import com.itrjp.im.api.service.ConnectNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ChannelController
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/30 01:06
 */
@RequestMapping("/channel")
@RestController
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;
    private final ConnectNodeService connectNodeService;
    private final TokenService tokenService;

    @PostMapping("/create")
    public Result<Channel> createChannel(@RequestBody @Validated CreateChannelParam param) {
        return Result.success(channelService.createChannel(param));
    }


    @GetMapping("connect")
    private Result<ChannelConnectInfo> connect(@Validated InitParam param) {

        String channelId = param.getChannelId();
        // 检查当前channelId是否存在
        channelService.checkChannelId(channelId);
        // 获取当前房间最佳节点
        List<ChannelNodeInfo> nodeInfo = connectNodeService.getBestNode(channelId);
        // 当前时间戳
        long currentTime = System.currentTimeMillis() / 1000;
        // 生成token
        String token = tokenService.create(channelId, currentTime, param.getUserId());

        return Result.success(new ChannelConnectInfo(channelId, nodeInfo, token, currentTime));
    }
}
