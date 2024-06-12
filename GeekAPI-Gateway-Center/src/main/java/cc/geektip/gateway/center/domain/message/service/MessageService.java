package cc.geektip.gateway.center.domain.message.service;

import cc.geektip.gateway.center.application.service.IMessageService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 接口消息服务
 * @author: Fish
 * @date: 2024/6/4
 */
@Service
public class MessageService implements IMessageService {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private Integer port;
    @Value("${spring.data.redis.database}")
    private Integer database;
    @Value("${spring.data.redis.password}")
    private String password;

    @Resource
    private MessagePublisher messagePublisher;

    @Override
    public Map<String, String> queryRedisConfig() {
        return new HashMap<>() {{
            put("host", host);
            put("port", String.valueOf(port));
            put("database", String.valueOf(database));
            put("password", password);
        }};
    }

    @Override
    public void pushMessage(String gatewayId, Object message) {
        messagePublisher.pushMessage(gatewayId, message);
    }
}
