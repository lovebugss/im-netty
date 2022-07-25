package com.itrjp.im.dispatcher.api;

import com.itrjp.im.dispatcher.pojo.ConnectInfo;
import com.itrjp.im.dispatcher.service.ConnectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/23 12:00
 */
@RestController
@RequestMapping("connect")
public class ConnectController {

    private final ConnectService connectService;

    public ConnectController(ConnectService connectService) {
        this.connectService = connectService;
    }

    /**
     * 获取连接信息
     *
     * @return
     */
    @GetMapping("{channelId}")
    public ResponseEntity<ConnectInfo> getConnectInfo(@PathVariable("channelId") String channelId) {

        ConnectInfo connectInfo = connectService.getConnectInfo(channelId);
        return ResponseEntity.ok(connectInfo);
    }
}
