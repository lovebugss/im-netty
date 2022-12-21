package com.itrjp.im.api.controller;

import com.itrjp.common.result.Result;
import com.itrjp.im.api.entity.MessageParam;
import com.itrjp.im.api.entity.SendMessageVo;
import com.itrjp.im.api.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/12/19 21:19
 */
@RestController
@RequestMapping("api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("send")
    public Result<SendMessageVo> send(@Validated @RequestBody MessageParam param) {
        // 发送消息
        String messageId = messageService.sendMessage(param);
        return Result.success(new SendMessageVo(messageId));
    }
}
