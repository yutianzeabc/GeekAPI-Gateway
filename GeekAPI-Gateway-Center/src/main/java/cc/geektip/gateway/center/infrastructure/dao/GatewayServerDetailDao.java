package cc.geektip.gateway.center.infrastructure.dao;

import cc.geektip.gateway.center.domain.operation.model.vo.GatewayServerDetailDataVO;
import cc.geektip.gateway.center.infrastructure.common.OperationRequest;
import cc.geektip.gateway.center.infrastructure.po.GatewayServerDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Fish
* @description 针对表【gateway_server_detail】的数据库操作Mapper
* @Entity cc.geektip.gateway.center.infrastructure.po.GatewayServerDetail
*/
public interface GatewayServerDetailDao extends BaseMapper<GatewayServerDetail> {

    GatewayServerDetail queryGatewayServerDetail(GatewayServerDetail gatewayServerDetail);

    boolean updateGatewayStatus(GatewayServerDetail gatewayServerDetail);

    List<GatewayServerDetail> queryGatewayServerDetailListByPage(OperationRequest<GatewayServerDetailDataVO> request);

    int queryGatewayServerDetailListCountByPage(OperationRequest<GatewayServerDetailDataVO> request);

}




