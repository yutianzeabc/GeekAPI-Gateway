package cc.geektip.gateway.sdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: 网关SDK配置类
 * @author: Fish
 * @date: 2024/6/4
 */
@Data
@ConfigurationProperties("api-gateway-sdk")
public class GatewaySDKProperties {

    /**
     * SDK服务上报是否启用
     */
    private boolean enabled = true;
    /**
     * 网关注册中心地址
     */
    private String address;
    /**
     * 系统标识
     */
    private String systemId;
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * RPC注册中心
     */
    private String systemRegistry;

}
