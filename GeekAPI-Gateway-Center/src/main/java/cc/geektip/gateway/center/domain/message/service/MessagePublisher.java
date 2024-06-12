package cc.geektip.gateway.center.domain.message.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Fish
 * @date: 2024/6/4
 */
@Service
@RequiredArgsConstructor
public class MessagePublisher {

    private final RedisTemplate<String, String> redisMessageTemplate;

    public void pushMessage(String topic, Object message) {
        redisMessageTemplate.convertAndSend(topic, message);
    }

}
