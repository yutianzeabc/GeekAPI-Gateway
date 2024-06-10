package cc.geektip.gateway.center.application;

import cc.geektip.gateway.center.domain.manage.aggregates.ApplicationSystemRichInfo;
import cc.geektip.gateway.center.domain.manage.model.vo.*;

import java.util.List;

/**
 * @description: 网关配置服务
 * @author: Fish
 * @date: 2024/5/28
 */
public interface IConfigManageService {

    List<GatewayServerVO> queryGatewayServerList();

    List<GatewayServerDetailVO> queryGatewayServerDetailList();

    List<GatewayDistributionVO> queryGatewayDistributionList();

    boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress);

    ApplicationSystemRichInfo queryApplicationSystemRichInfo(String gatewayId, String systemId);

    String queryGatewayDistribution(String systemId);

    List<ApplicationSystemVO> queryApplicationSystemList();

    List<ApplicationInterfaceVO> queryApplicationInterfaceList();

    List<ApplicationInterfaceMethodVO> queryApplicationInterfaceMethodList();

}
