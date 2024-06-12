package cc.geektip.gateway.center.infrastructure.dao;

import cc.geektip.gateway.center.domain.operation.model.vo.ApplicationInterfaceMethodDataVO;
import cc.geektip.gateway.center.infrastructure.common.OperationRequest;
import cc.geektip.gateway.center.infrastructure.po.ApplicationInterfaceMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Fish
* @description 针对表【application_interface_method】的数据库操作Mapper
* @Entity cc.geektip.gateway.center.infrastructure.po.ApplicationInterfaceMethod
*/
public interface ApplicationInterfaceMethodDao extends BaseMapper<ApplicationInterfaceMethod> {

    boolean updateApplicationInterfaceMethod(ApplicationInterfaceMethod req);

    List<ApplicationInterfaceMethod> queryApplicationInterfaceMethodList(ApplicationInterfaceMethod req);

    List<ApplicationInterfaceMethod> queryApplicationInterfaceMethodListByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request);

    int queryApplicationInterfaceMethodListCountByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request);

    boolean isExistByInterfaceMethodId(ApplicationInterfaceMethod applicationInterfaceMethod);

}




