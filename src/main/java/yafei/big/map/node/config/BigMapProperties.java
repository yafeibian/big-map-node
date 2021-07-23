package yafei.big.map.node.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="bigmap")
public class BigMapProperties {
    private String clientName;
    private String clientUrl;
    private String serverUrl;
}
