package com.itrjp.im.api.service.impl;

import com.itrjp.im.api.entity.ConnectNode;
import com.itrjp.im.api.pojo.param.consul.Checks;
import com.itrjp.im.api.pojo.param.consul.ConsulWatchServiceEntity;
import com.itrjp.im.api.service.ConnectNodeService;
import com.itrjp.im.api.service.NodeWatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:r979668507@gmail.com">renjp</a>
 * @date 2022/7/26 15:29
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NodeWatchServiceImpl implements NodeWatchService {

    private final ConnectNodeService connectNodeService;

    @Override
    public void handlerServiceNotify(List<ConsulWatchServiceEntity> param) {

        // 获取可用列表
        List<ConnectNode> availableList = connectNodeService.getAvailableList();
        // 解析最近列表
        List<ConnectNode> connectNodes = resolveServiceNotify(param);
        // 查找下线服务
        List<ConnectNode> offlineList = findOffline(availableList, connectNodes);
        // 查找上线服务
        List<ConnectNode> onlineList = findOnline(availableList, connectNodes);
        offlineList.forEach(connectNodeService::offline);
        onlineList.forEach(connectNodeService::online);
    }

    /**
     * 查找上线服务
     *
     * @param availableList
     * @param connectNodes
     * @return
     */
    private List<ConnectNode> findOnline(List<ConnectNode> availableList, List<ConnectNode> connectNodes) {
        ArrayList<ConnectNode> result = new ArrayList<>(connectNodes);
        result.removeAll(availableList);
        return result;
    }

    /**
     * 查找上线服务
     *
     * @param availableList 可用列表
     * @param connectNodes  最新列表
     * @return
     */
    private List<ConnectNode> findOffline(List<ConnectNode> availableList, List<ConnectNode> connectNodes) {
        // 查找connectNodes 中不存在的数据
        ArrayList<ConnectNode> result = new ArrayList<>(availableList);
        result.removeAll(connectNodes);
        return result;
    }

    private List<ConnectNode> resolveServiceNotify(List<ConsulWatchServiceEntity> param) {
        if (param == null || param.isEmpty()) {
            return new ArrayList<>();
        }
        return param.stream()
                .filter((entity -> {
                    List<Checks> checks = entity.getChecks();
                    if (checks == null || checks.isEmpty()) {
                        return false;
                    }
                    for (Checks check : checks) {
                        if (check.getStatus().equals("passing")) {
                            return true;
                        }
                    }
                    return false;
                }))
                .map(entity -> {
                    com.itrjp.im.api.pojo.param.consul.Service service = entity.getService();
                    String address = service.getAddress();
                    String wsPort = service.getMeta().get("wsPort").toString();
                    int port = service.getPort();
                    String id = service.getId();
                    return new ConnectNode(id, address, port, Integer.parseInt(wsPort));
                }).toList();
    }
}
