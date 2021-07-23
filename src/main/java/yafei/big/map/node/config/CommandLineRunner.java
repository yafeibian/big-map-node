package yafei.big.map.node.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import yafei.big.map.node.constant.MapConstant;
import yafei.big.map.node.entity.dto.NodeStatus;
import yafei.big.map.node.entity.vo.Result;
import yafei.big.map.node.service.NodeStatusService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
//@EnableScheduling   // 2.开启定时任务
public class CommandLineRunner implements ApplicationRunner{
    @Resource
    private BigMapProperties bigMapProperties;
    @Resource
    private NodeStatusService nodeStatusService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("地图切片节点服务启动成功00000000000000000000000");
        NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setLastHeartTIme(new Date());
        nodeStatus.setName(bigMapProperties.getClientName());
        nodeStatus.setUrl(bigMapProperties.getClientUrl());
        nodeStatus.setStatus(MapConstant.FREE);
        Boolean result = nodeStatusService.reportNodeStatus(nodeStatus);
        if(result){
            log.info("上报成功,上报时间："+nodeStatus.getLastHeartTIme());
        }else{
            log.error("上报失败");
        }
    }
    // @Scheduled(fixedRate=5000)
    public void reportNodeStatus(){

        log.info("地图切片节点服务启动成功00000000000000000000000");
        NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setLastHeartTIme(new Date());
        nodeStatus.setName(bigMapProperties.getClientName());
        nodeStatus.setUrl(bigMapProperties.getClientUrl());
        //nodeStatus.setStatus(MapConstant.FREE);
        boolean b = nodeStatusService.reportNodeStatus(nodeStatus);
        if(b){
            log.info("上报成功,上报时间："+nodeStatus.getLastHeartTIme());
        }else{
            log.error("上报失败");
        }
        //System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }


}


