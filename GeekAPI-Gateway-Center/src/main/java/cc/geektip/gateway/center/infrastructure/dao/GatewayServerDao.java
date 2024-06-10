package cc.geektip.gateway.center.infrastructure.dao;

import cc.geektip.gateway.center.infrastructure.common.OperationRequest;
import cc.geektip.gateway.center.infrastructure.po.GatewayServer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Fish
* @description 针对表【gateway_server】的数据库操作Mapper
* @Entity cc.geektip.gateway.center.infrastructure.po.GatewayServer
*/
public interface GatewayServerDao extends BaseMapper<GatewayServer> {

    List<GatewayServer> queryGatewayServerListByPage(OperationRequest<String> request);

    int queryGatewayServerListCountByPage(OperationRequest<String> request);

}




