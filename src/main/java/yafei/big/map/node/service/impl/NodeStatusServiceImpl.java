package yafei.big.map.node.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yafei.big.map.node.config.BigMapProperties;
import yafei.big.map.node.constant.MapConstant;
import yafei.big.map.node.entity.dto.NodeStatus;
import yafei.big.map.node.entity.vo.Result;
import yafei.big.map.node.feign.NodeStatusFeign;
import yafei.big.map.node.service.NodeStatusService;

import javax.annotation.Resource;
import java.util.Date;
@Slf4j
@Service
public class NodeStatusServiceImpl implements NodeStatusService {
    @Resource
    private BigMapProperties bigMapProperties;
    @Resource
    private NodeStatusFeign nodeStatusFeign;

    @Override
    public boolean reportNodeStatus(NodeStatus nodeStatus) {
        Result result = nodeStatusFeign.reportNodeStatus(nodeStatus);
        return result.isSuccess();
    }

    @Override
    public boolean reportNodeFree() {
        log.info("上报节点空闲ffffffff");
        NodeStatus nodeStatus1 = new NodeStatus();
        nodeStatus1.setStatus(MapConstant.FREE);
        nodeStatus1.setLastEndWorkTime(new Date());
        nodeStatus1.setName(bigMapProperties.getClientName());
        Result result = nodeStatusFeign.reportNodeStatus(nodeStatus1);
        return result.isSuccess();
    }

    @Override
    public boolean reportNodeWork() {
        log.info("上报节点开始工作wwwwwwwwwwwwwww");
        NodeStatus nodeStatus1 = new NodeStatus();
        nodeStatus1.setStatus(MapConstant.WORK);
        nodeStatus1.setLastStartWorkTime(new Date());
        nodeStatus1.setName(bigMapProperties.getClientName());
        Result result = nodeStatusFeign.reportNodeStatus(nodeStatus1);
        return result.isSuccess();
    }
}
