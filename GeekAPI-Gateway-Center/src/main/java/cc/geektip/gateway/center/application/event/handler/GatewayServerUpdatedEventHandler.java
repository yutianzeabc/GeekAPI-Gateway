package cc.geektip.gateway.center.application.event.handler;

import cc.geektip.gateway.center.application.event.GatewayServerUpdatedEvent;
import cc.geektip.gateway.center.application.service.IConfigManageService;
import cc.geektip.gateway.center.application.service.ILoadBalanceService;
import cc.geektip.gateway.center.domain.loadbalance.model.aggregates.CaddyConfig;
import cc.geektip.gateway.center.domain.loadbalance.model.vo.ProxyConfigVO;
import cc.geektip.gateway.center.domain.manage.model.vo.GatewayServerDetailVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 网关算力节点注册事件处理器
 * @author: Fish
 * @date: 2024/6/11
 */
@Slf4j
@Component
public class GatewayServerUpdatedEventHandler {

    @Resource
    private IConfigManageService configManageService;

    @Resource
    private ILoadBalanceService loadBalanceService;

    @Async
    @EventListener(GatewayServerUpdatedEvent.class)
    public void onGatewayServerUpdatedEvent(GatewayServerUpdatedEvent event) {
        // 1. 读取最新网关算力数据
        List<GatewayServerDetailVO> gatewayServerDetailVOList = configManageService.queryGatewayServerDetailList();
        // 2. 组建Caddy反向代理配置
        List<ProxyConfigVO> proxyConfigVOList = gatewayServerDetailVOList.stream()
                .collect(Collectors.groupingBy(GatewayServerDetailVO::getGroupId))
                .entrySet().stream()
                .map(entry -> new ProxyConfigVO(entry.getKey(), entry.getValue().stream()
                        .map(GatewayServerDetailVO::getGatewayAddress)
                        .toList()))
                .toList();
        CaddyConfig caddyConfig = new CaddyConfig(proxyConfigVOList);
        // 3. 刷新Caddy配置
        try {
            loadBalanceService.updateCaddyConfig(caddyConfig);
            log.info("刷新Caddy配置完成");
        } catch (Exception e) {
            log.error("刷新Caddy配置异常：", e);
        }
    }

}
