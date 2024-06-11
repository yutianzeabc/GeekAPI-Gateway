package cc.geektip.gateway.starter.domain.service;

import cc.geektip.gateway.starter.config.GatewayServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description: 心跳发布者
 * @author: Fish
 * @date: 2024/6/4
 */

@Slf4j
@RequiredArgsConstructor
public class HeartbeatPublisher {

    private static final String TOPIC = "gateway:heartbeat";

    private final RedisTemplate<String, Object> redisMessageTemplate;

    private final GatewayServiceProperties gatewayServiceProperties;

    public void doHeartBeat() {
        String message = String.format("%s:%s", gatewayServiceProperties.getGroupId(), gatewayServiceProperties.getGatewayId());
        redisMessageTemplate.convertAndSend(TOPIC, message);
    }

}
