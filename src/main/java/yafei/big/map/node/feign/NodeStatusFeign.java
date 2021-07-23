package yafei.big.map.node.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yafei.big.map.node.entity.dto.NodeStatus;
import yafei.big.map.node.entity.vo.Result;
import yafei.big.map.node.feign.impl.NodeStatusFeignFallBack;

@FeignClient(name = "big-map-server",url = "${bigmap.serverUrl}",fallback = NodeStatusFeignFallBack.class)
public interface NodeStatusFeign {

    @PostMapping("/map/reportNodeStatus")
    Result reportNodeStatus(@RequestBody NodeStatus nodeStatus);
}
