package cc.geektip.gateway.center.infrastructure.dao;

import cc.geektip.gateway.center.domain.operation.model.vo.GatewayDistributionDataVO;
import cc.geektip.gateway.center.infrastructure.common.OperationRequest;
import cc.geektip.gateway.center.infrastructure.po.GatewayDistribution;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Fish
* @description 针对表【gateway_distribution】的数据库操作Mapper
* @Entity cc.geektip.gateway.center.infrastructure.po.GatewayDistribution
*/
public interface GatewayDistributionDao extends BaseMapper<GatewayDistribution> {

    List<String> queryGatewayDistributionSystemIdList();

    String queryGatewayDistribution(String systemId);

    List<GatewayDistribution> queryGatewayDistributionListByPage(OperationRequest<GatewayDistributionDataVO> request);

    int queryGatewayDistributionListCountByPage(OperationRequest<GatewayDistributionDataVO> request);

}




