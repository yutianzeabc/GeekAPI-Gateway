package cc.geektip.gateway.center.domain.manage.repository;

import cc.geektip.gateway.center.domain.manage.model.vo.*;

import java.util.List;

/**
 * @description: 网关配置仓储
 * @author: Fish
 * @date: 2024/5/28
 */
public interface IConfigManageRepository {

    List<GatewayServerVO> queryGatewayServerList();

    List<GatewayServerDetailVO> queryGatewayServerDetailList();

    boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress, Integer available);

    GatewayServerDetailVO queryGatewayServerDetail(String gatewayId, String gatewayAddress);

    boolean updateGatewayStatus(String gatewayId, String gatewayAddress, Integer available);

    List<String> queryGatewayDistributionSystemIdList(String gatewayId);

    List<ApplicationSystemVO> queryApplicationSystemList(List<String> systemIdList);

    List<ApplicationInterfaceVO> queryApplicationInterfaceList(String systemId);

    List<ApplicationInterfaceMethodVO> queryApplicationInterfaceMethodList(String systemId, String interfaceId);

    String queryGatewayDistribution(String systemId);

    List<GatewayDistributionVO> queryGatewayDistributionList();

}
