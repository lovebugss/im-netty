package com.itrjp.im.api.controller;

import com.itrjp.common.result.Result;
import com.itrjp.im.api.entity.MessageParam;
import com.itrjp.im.api.entity.SendMessageVo;
import com.itrjp.im.api.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("message")
@RequiredArgsConstructor
@Api(value = "message", tags = "消息管理")
@Validated
public class MessageController {

    private final MessageService messageService;

    @PostMapping(value = "send", produces = "application/json", consumes = "application/json")
    @ApiOperation(value = "发送消息", nickname = "messageSendPost", notes = "", response = Result.class, tags = {"IM/消息管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    public Result<SendMessageVo> send(@Validated @RequestBody MessageParam param) {
        // 发送消息
        String messageId = messageService.sendMessage(param);
        return Result.success(new SendMessageVo(messageId));
    }
}
