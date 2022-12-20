package com.itrjp.im.api.controller;

import com.itrjp.common.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/11/19 21:34
 */
@RestController
@RequestMapping("connect")
public class ConnectController {


    /**
     * 获取长链接信息
     *
     * @param channelId
     * @return
     */
    @PostMapping("get")
    public Result<?> init(String channelId) {
        return Result.success(null);
    }
}
