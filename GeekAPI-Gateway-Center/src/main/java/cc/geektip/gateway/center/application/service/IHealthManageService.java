package cc.geektip.gateway.center.application.service;

/**
 * @description: 健康管理服务
 * @author: Fish
 * @date: 2024/6/11
 */
public interface IHealthManageService {

    boolean triggerGatewayServerNodeOnline(String groupId, String gatewayId);

    boolean triggerGatewayServerNodeOffline(String groupId, String gatewayId);

}
