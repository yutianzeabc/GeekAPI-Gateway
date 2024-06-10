package cc.geektip.gateway.starter.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 网关服务注册配置
 * @author: Fish
 * @date: 2024/5/30
 */
@Data
@ConfigurationProperties("api-gateway")
public class GatewayServiceProperties {

    /**
     * 注册中心
     */
    private String address;
    /**
     * 分组ID
     */
    private String groupId;
    /**
     * 网关ID
     */
    private String gatewayId;
    /**
     * 网关名称
     */
    private String gatewayName;
    /**
     * 网关地址
     */
    private String gatewayAddress;

}
