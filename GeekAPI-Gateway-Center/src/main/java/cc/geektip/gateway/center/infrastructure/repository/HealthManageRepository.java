package cc.geektip.gateway.center.infrastructure.repository;

import cc.geektip.gateway.center.domain.health.repository.IHealthManageRepository;
import cc.geektip.gateway.center.infrastructure.common.Constants;
import cc.geektip.gateway.center.infrastructure.dao.GatewayServerDetailDao;
import cc.geektip.gateway.center.infrastructure.po.GatewayServerDetail;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @description: 健康管理仓储
 * @author: Fish
 * @date: 2024/6/11
 */
@Repository
public class HealthManageRepository implements IHealthManageRepository {

    @Resource
    private GatewayServerDetailDao gatewayServerDetailDao;

    @Override
    public boolean setGatewayServerNodeOnline(String groupId, String gatewayId) {
        GatewayServerDetail req = new GatewayServerDetail();
        req.setGroupId(groupId);
        req.setGatewayId(gatewayId);
        req.setStatus(Constants.GatewayStatus.Available);
        return gatewayServerDetailDao.updateGatewayStatus(req);
    }

    @Override
    public boolean setGatewayServerNodeOffline(String groupId, String gatewayId) {
        GatewayServerDetail req = new GatewayServerDetail();
        req.setGroupId(groupId);
        req.setGatewayId(gatewayId);
        req.setStatus(Constants.GatewayStatus.UnAvailable);
        return gatewayServerDetailDao.updateGatewayStatus(req);
    }

}
