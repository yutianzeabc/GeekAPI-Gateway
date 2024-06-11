package cc.geektip.gateway.center.domain.health.repository;

/**
 * @description: 健康管理仓储服务接口
 * @author: Fish
 * @date: 2024/6/11
 */
public interface IHealthManageRepository {

    boolean setGatewayServerNodeOnline(String groupId, String gatewayId);

    boolean setGatewayServerNodeOffline(String groupId, String gatewayId);

}
