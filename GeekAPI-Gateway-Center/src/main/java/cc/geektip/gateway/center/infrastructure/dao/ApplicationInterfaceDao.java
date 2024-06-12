package cc.geektip.gateway.center.infrastructure.dao;

import cc.geektip.gateway.center.domain.operation.model.vo.ApplicationInterfaceDataVO;
import cc.geektip.gateway.center.infrastructure.common.OperationRequest;
import cc.geektip.gateway.center.infrastructure.po.ApplicationInterface;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Fish
* @description 针对表【application_interface】的数据库操作Mapper
* @Entity cc.geektip.gateway.center.infrastructure.po.ApplicationInterface
*/
public interface ApplicationInterfaceDao extends BaseMapper<ApplicationInterface> {

    boolean updateApplicationInterface(ApplicationInterface req);

    List<ApplicationInterface> queryApplicationInterfaceList(String systemId);

    List<ApplicationInterface> queryApplicationInterfaceListByPage(OperationRequest<ApplicationInterfaceDataVO> request);

    int queryApplicationInterfaceListCountByPage(OperationRequest<ApplicationInterfaceDataVO> request);

    boolean isExistByInterfaceId(ApplicationInterface applicationInterface);

}




