package yafei.big.map.node.service;

import yafei.big.map.node.entity.dto.NodeStatus;
public interface NodeStatusService {

    boolean reportNodeStatus(NodeStatus nodeStatus);

    boolean reportNodeFree();

    boolean reportNodeWork();
}
