package cc.geektip.gateway.sdk.config;

import cc.geektip.gateway.sdk.application.GatewaySDKApplication;
import cc.geektip.gateway.sdk.domain.service.GatewayCenterService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 网关SDK自动配置类
 * @author: Fish
 * @date: 2024/6/4
 */
@Configuration
@EnableConfigurationProperties(GatewaySDKProperties.class)
public class GatewaySDKAutoConfig {

    @Bean
    public GatewayCenterService gatewayCenterService() {
        return new GatewayCenterService();
    }

    @Bean
    public GatewaySDKApplication gatewaySDKApplication(GatewaySDKProperties properties, GatewayCenterService gatewayCenterService) {
        return new GatewaySDKApplication(properties, gatewayCenterService);
    }

}
