package cc.geektip.gateway.center.domain.health.service;

import cc.geektip.gateway.center.application.event.GatewayServerUpdatedEvent;
import cc.geektip.gateway.center.application.service.IHealthManageService;
import cc.geektip.gateway.center.domain.health.repository.IHealthManageRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @description: 健康管理服务
 * @author: Fish
 * @date: 2024/6/11
 */
@Slf4j
@Service
public class HealthManageService implements IHealthManageService {

    @Resource
    private IHealthManageRepository healthManageRepository;
    @Resource
    private ApplicationEventPublisher eventPublisher;
    private final Cache<String, LocalDateTime> nodeStatusCache;

    private static final Long TIMEOUT_MINUTES = 5L;

    public HealthManageService() {
        nodeStatusCache = Caffeine.newBuilder()
                .expireAfterAccess(TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .removalListener((RemovalListener<String, LocalDateTime>) (key, localDateTime, removalCause) -> {
                    if (removalCause == RemovalCause.EXPIRED || removalCause == RemovalCause.EXPLICIT) {
                        String[] parts = key.split(":");
                        String groupId = parts[0];
                        String gatewayId = parts[1];
                        if (removalCause == RemovalCause.EXPIRED) {
                            log.info("网关节点执行超时下线：groupId={}, gatewayId={}", groupId, gatewayId);
                        } else {
                            log.info("网关节点执行主动下线：groupId={}, gatewayId={}", groupId, gatewayId);
                        }
                        handleGatewayServerNodeOffline(groupId, gatewayId);
                    }
                })
                .build();
    }

    @Override
    public boolean triggerGatewayServerNodeOnline(String groupId, String gatewayId) {
        nodeStatusCache.put(String.format("%s:%s", groupId, gatewayId), LocalDateTime.now());
        return handleGatewayServerNodeOnline(groupId, gatewayId);
    }

    @Override
    public boolean triggerGatewayServerNodeOffline(String groupId, String gatewayId) {
        nodeStatusCache.invalidate(String.format("%s:%s", groupId, gatewayId));
        nodeStatusCache.cleanUp();
        return handleGatewayServerNodeOffline(groupId, gatewayId);
    }

    public boolean handleGatewayServerNodeOnline(String groupId, String gatewayId) {
        boolean done = healthManageRepository.setGatewayServerNodeOnline(groupId, gatewayId);
        if (done) {
            eventPublisher.publishEvent(new GatewayServerUpdatedEvent(this));
            log.info("网关节点已上线：groupId={}, gatewayId={}", groupId, gatewayId);
        } else {
            log.warn("网关节点上线失败：groupId={}, gatewayId={}", groupId, gatewayId);
        }
        return done;
    }

    public boolean handleGatewayServerNodeOffline(String groupId, String gatewayId) {
        boolean done = healthManageRepository.setGatewayServerNodeOffline(groupId, gatewayId);
        if (done) {
            eventPublisher.publishEvent(new GatewayServerUpdatedEvent(this));
            log.info("网关节点已下线：groupId={}, gatewayId={}", groupId, gatewayId);
        } else {
            log.warn("网关节点下线失败：groupId={}, gatewayId={}", groupId, gatewayId);
        }
        return done;
    }

    public void receiveHeartbeat(Message message) {
        String key = new String(message.getBody());
        String[] parts = key.split(":");
        String groupId = parts[0];
        String gatewayId = parts[1];
        nodeStatusCache.asMap().compute(key, (k, v) -> {
            if (null == v) {
                handleGatewayServerNodeOnline(groupId, gatewayId);
            }
            return LocalDateTime.now();
        });
    }

}