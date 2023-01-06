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
import com.itrjp.im.api.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Validated
@Api(value = "channel", tags = "频道管理")
public class ChannelController {
    private final ChannelService channelService;
    private final ConnectNodeService connectNodeService;
    private final TokenService tokenService;
    private final UserService userService;

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

    /**
     * POST /channel/{channelId}/broadcast : 广播消息
     *
     * @param channelId (required)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "广播消息", nickname = "channelChannelIdBroadcastPost", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @PostMapping(value = "/channel/{channelId}/broadcast",
            produces = {"application/json"})
    private Result channelBroadcast(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId) {
        return Result.success();
    }


    /**
     * DELETE /channel/{channelId} : 删除房间
     *
     * @param channelId (required)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "删除房间", nickname = "channelChannelIdDelete", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @DeleteMapping(value = "/channel/{channelId}",
            produces = {"application/json"})
    private Result deleteChannel(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId) {
        return Result.success();

    }


    /**
     * PUT /channel/{channelId}/filter/{filter} : 修复频道消息过滤类型
     *
     * @param channelId (required)
     * @param type      (required)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "修改频道消息过滤类型", nickname = "channelChannelIdFilterFilterPut", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @PutMapping(value = "/channel/{channelId}/filter/{type}",
            produces = {"application/json"})
    private Result changeChannelFilterType(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId,
                                           @ApiParam(value = "", required = true) @PathVariable("type") String type) {
        return Result.success(null);

    }


    /**
     * GET /channel/{channelId} : 获取房间配置
     *
     * @param channelId (required)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "获取房间配置", nickname = "channelChannelIdGet", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @GetMapping(value = "/channel/{channelId}",
            produces = {"application/json"})
    private Result channelChannelIdGet(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId) {

        return Result.success(null);

    }


    /**
     * GET /channel/{channelId}/init : 初始化连接信息
     *
     * @param channelId (required)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "初始化连接信息", nickname = "channelChannelIdInitGet", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @GetMapping(value = "/channel/{channelId}/init",
            produces = {"application/json"})
    private Result channelChannelIdInitGet(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId) {

        return Result.success(null);

    }


    /**
     * PUT /channel/{channelId}/limit/{limit} : 修改频道最大limit
     *
     * @param channelId (required)
     * @param limit     (required)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "修改频道最大limit", nickname = "channelChannelIdLimitLimitPut", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @PutMapping(value = "/channel/{channelId}/limit/{limit}",
            produces = {"application/json"})
    private Result channelChannelIdLimitLimitPut(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId,
                                                 @ApiParam(value = "", required = true) @PathVariable("limit") String limit) {

        return Result.success(null);

    }


    /**
     * PUT /channel/{channelId} : 更新房间配置
     *
     * @param channelId (required)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "更新房间配置", nickname = "channelChannelIdPut", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @PutMapping(value = "/channel/{channelId}",
            produces = {"application/json"})
    private Result channelChannelIdPut(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId) {
        return Result.success(null);

    }

    /**
     * POST /channel/{channelId}/mute/{userId} : 频道内禁言某用户
     *
     * @param channelId (required)
     * @param userId    (required)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "频道内禁言某用户", nickname = "channelChannelIdMuteUserIdPost", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @PostMapping(value = "/channel/{channelId}/mute/{userId}",
            produces = {"application/json"})
    private Result<Void> muteChannelUser(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId,
                                         @ApiParam(value = "", required = true) @PathVariable("userId") String userId) {

        channelService.checkChannelId(channelId);
        // 检查用户是否存在
        userService.checkUserExist(userId);

        // 检查用户是否在频道内
        channelService.checkUserInChannel(channelId, userId);

        // 禁言用户
        channelService.muteUser(channelId, userId);
        return Result.success();

    }

    /**
     * DELETE /channel/{channelId}/mute/{userId} : 解除禁言
     *
     * @param channelId (required)
     * @param userId    (required)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "解除禁言", nickname = "channelChannelIdUnmuteUserIdPost", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @DeleteMapping(value = "/channel/{channelId}/mute/{userId}",
            produces = {"application/json"})
    private Result<Void> unMuteChannelUser(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId,
                                           @ApiParam(value = "", required = true) @PathVariable("userId") String userId) {
        channelService.checkChannelId(channelId);
        // 检查用户是否存在
        userService.checkUserExist(userId);
        // 检查用户是否在频道内
        channelService.checkUserInChannel(channelId, userId);
        // 禁言用户
        channelService.unMuteUser(channelId, userId);
        return Result.success();
    }


    /**
     * DELETE /channel/{channelId}/dismiss : 解散房间
     *
     * @param channelId (required)
     * @param body      (optional)
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "解散房间", nickname = "channelchannelIdDismissDelete", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @DeleteMapping(value = "/channel/{channelId}/dismiss",
            produces = {"application/json"},
            consumes = {"application/json"})
    private Result channelChannelIdDismissDelete(@ApiParam(value = "", required = true) @PathVariable("channelId") String channelId,
                                                 @ApiParam(value = "") @Valid @RequestBody(required = false) Object body) {

        return Result.success(null);

    }


    /**
     * GET /channel : 获取房间列表
     *
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "获取房间列表", nickname = "channelGet", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @GetMapping(value = "/channel",
            produces = {"application/json"})
    private Result<Channel> channelGet() {

        return Result.success(null);
    }


    /**
     * POST /channel : 创建频道
     *
     * @return 成功 (status code 200)
     */
    @ApiOperation(value = "创建频道", nickname = "channelPost", notes = "", response = Result.class, tags = {"IM/频道管理",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功", response = Result.class)})
    @PostMapping(value = "/channel",
            produces = {"application/json"})
    private Result<Channel> channelPost() {
        return Result.success(null);

    }
}
