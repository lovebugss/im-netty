package com.itrjp.im.api.controller;

import com.itrjp.common.result.Result;
import com.itrjp.im.api.entity.Channel;
import com.itrjp.im.api.pojo.param.CreateChannelParam;
import com.itrjp.im.api.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/create")
    public Result<Channel> createChannel(@RequestBody @Validated CreateChannelParam param) {
        return Result.success(channelService.createChannel(param));
    }
}
