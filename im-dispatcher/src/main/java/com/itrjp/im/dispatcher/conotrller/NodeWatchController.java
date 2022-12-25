package com.itrjp.im.dispatcher.conotrller;

import com.itrjp.im.dispatcher.pojo.ConsulWatchServiceEntity;
import com.itrjp.im.dispatcher.service.NodeWatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/***
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/26 14:48
 */
@Slf4j
@RestController
@RequestMapping("consul")
public class NodeWatchController {

    private final NodeWatchService nodeWatchService;

    public NodeWatchController(NodeWatchService nodeWatchService) {
        this.nodeWatchService = nodeWatchService;
    }

    @PostMapping("/watch/checks")
    public String checks(@RequestBody List<Map<String, Object>> param) {
        log.info("event: checks, param: {}", param);
        return "success";
    }

    @PostMapping("/watch/services")
    public String services(@RequestBody Map<String, Object> param) {
        log.info("event: services, param: {}", param);
        return "success";
    }

    @PostMapping("/watch/nodes")
    public String nodes(@RequestBody List<Map<String, Object>> param) {
        log.info("event: nodes, param: {}", param);
        return "success";
    }

    @PostMapping("/watch/service")
    public String service(@RequestBody List<ConsulWatchServiceEntity> param) {
        log.info("event: service, param: {}", param);
        nodeWatchService.handlerServiceNotify(param);
        return "success";
    }

    @PostMapping("/watch/event")
    public String event(@RequestBody List<Map<String, Object>> param) {
        log.info("event: event, param: {}", param);
        return "success";
    }
}
