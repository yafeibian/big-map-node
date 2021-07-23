package yafei.big.map.node.feign.impl;

import yafei.big.map.node.entity.dto.NodeStatus;
import yafei.big.map.node.entity.vo.Result;
import yafei.big.map.node.feign.NodeStatusFeign;

public class NodeStatusFeignFallBack implements NodeStatusFeign {
    @Override
    public Result reportNodeStatus(NodeStatus nodeStatus) {
        return Result.fail("无法连接bigmap调度中心");
    }
}
