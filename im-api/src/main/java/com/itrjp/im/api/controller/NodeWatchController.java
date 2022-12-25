package com.itrjp.im.api.controller;

import com.itrjp.im.api.service.NodeWatchService;
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
@RestController
@RequestMapping("consul")
public class NodeWatchController {

    private final NodeWatchService nodeWatchService;

    public NodeWatchController(NodeWatchService nodeWatchService) {
        this.nodeWatchService = nodeWatchService;
    }

    @PostMapping("/watch")
    public String watch(@RequestBody List<Map<String, Object>> param) {
        System.out.println(param);
        nodeWatchService.watch(param);
        return "success";
    }
}
