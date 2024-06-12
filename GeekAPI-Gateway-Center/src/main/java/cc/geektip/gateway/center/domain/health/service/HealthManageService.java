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
import org.springframework.scheduling.annotation.Scheduled;
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

    private static final Long TIMEOUT_MINUTES = 5L;
    private final Cache<String, LocalDateTime> nodeStatusCache;
    @Resource
    private IHealthManageRepository healthManageRepository;
    @Resource
    private ApplicationEventPublisher eventPublisher;

    public HealthManageService() {
        nodeStatusCache = Caffeine.newBuilder()
                .expireAfterAccess(TIMEOUT_MINUTES, TimeUnit.MINUTES)
                .removalListener((RemovalListener<String, LocalDateTime>) (key, localDateTime, removalCause) -> {
                    if (removalCause == RemovalCause.EXPIRED) {
                        String[] parts = key.split(":");
                        String groupId = parts[0];
                        String gatewayId = parts[1];
                        log.info("网关节点执行超时下线：groupId={}, gatewayId={}", groupId, gatewayId);
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

    public void receiveHeartbeat(Object message) {
        String key = message.toString();
        String[] parts = key.split(":");
        String groupId = parts[0];
        String gatewayId = parts[1];
        log.debug("网关节点心跳：groupId={}, gatewayId={}", groupId, gatewayId);
        nodeStatusCache.asMap().compute(key, (k, v) -> {
            if (null == v) {
                handleGatewayServerNodeOnline(groupId, gatewayId);
            }
            return LocalDateTime.now();
        });
    }

    @Scheduled(fixedRate = 300000)
    public void checkNodeStatus() {
        nodeStatusCache.cleanUp();
    }

}