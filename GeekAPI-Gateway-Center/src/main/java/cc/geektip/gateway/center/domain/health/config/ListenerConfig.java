package cc.geektip.gateway.center.domain.health.config;

import cc.geektip.gateway.center.application.service.IHealthManageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @description: Redis 消息监听配置
 * @author: Fish
 * @date: 2024/6/11
 */
@Configuration
public class ListenerConfig {

    public static final String CHANNEL = "gateway:heartbeat";

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter msgAgreementListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(msgAgreementListenerAdapter, new PatternTopic(CHANNEL));
        return container;
    }

    @Bean
    public MessageListenerAdapter msgAgreementListenerAdapter(IHealthManageService healthManageService) {
        return new MessageListenerAdapter(healthManageService, "receiveHeartbeat");
    }

}
