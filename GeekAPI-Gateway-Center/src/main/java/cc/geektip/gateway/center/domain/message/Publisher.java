package cc.geektip.gateway.center.domain.message;

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
public class Publisher {

    private final RedisTemplate<String, Object> redisMessageTemplate;

    public void pushMessage(String topic, Object message) {
        redisMessageTemplate.convertAndSend(topic, message);
    }

}
