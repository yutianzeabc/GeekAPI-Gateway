package cc.geektip.gateway.center.infrastructure.dao;

import cc.geektip.gateway.center.domain.operation.model.vo.ApplicationSystemDataVO;
import cc.geektip.gateway.center.infrastructure.common.OperationRequest;
import cc.geektip.gateway.center.infrastructure.po.ApplicationSystem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Fish
* @description 针对表【application_system】的数据库操作Mapper
* @Entity cc.geektip.gateway.center.infrastructure.po.ApplicationSystem
*/
public interface ApplicationSystemDao extends BaseMapper<ApplicationSystem> {

    boolean updateApplicationSystem(ApplicationSystem req);

    List<ApplicationSystem> queryApplicationSystemList(List<String> list);

    List<ApplicationSystem> queryApplicationSystemListByPage(OperationRequest<ApplicationSystemDataVO> request);

    int queryApplicationSystemListCountByPage(OperationRequest<ApplicationSystemDataVO> request);

    String queryApplicationSystemName(String systemId);

    boolean isExistBySystemId(String systemId);

}




