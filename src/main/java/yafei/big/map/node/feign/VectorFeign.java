package yafei.big.map.node.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yafei.big.map.node.entity.po.Vector;
import yafei.big.map.node.entity.vo.Result;
import yafei.big.map.node.feign.impl.VectorFeignFallBack;

@FeignClient( name = "map-vector-record-server",url = "${bigmap.serverUrl}",fallback = VectorFeignFallBack.class)
public interface VectorFeign {
    @PostMapping("/map/reportVectorRecord")
    Result reportVectorRecord(@RequestBody  Vector vector);
}
