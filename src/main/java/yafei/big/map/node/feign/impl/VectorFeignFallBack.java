package yafei.big.map.node.feign.impl;

import yafei.big.map.node.entity.dto.NodeStatus;
import yafei.big.map.node.entity.po.Vector;
import yafei.big.map.node.entity.vo.Result;
import yafei.big.map.node.feign.NodeStatusFeign;
import yafei.big.map.node.feign.VectorFeign;


public class VectorFeignFallBack implements VectorFeign {


    @Override
    public Result reportVectorRecord(Vector vector) {
        return Result.fail("无法连接bigmap调度中心");
    }
}
