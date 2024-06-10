package cc.geektip.gateway.center.application;

import cc.geektip.gateway.center.domain.loadbalance.model.aggregates.CaddyConfig;

/**
 * @description: 负载均衡配置服务
 * @author: Fish
 * @date: 2024/6/6
 */
public interface ILoadBalanceService {

    void updateCaddyConfig(CaddyConfig caddyConfig) throws Exception;

}
