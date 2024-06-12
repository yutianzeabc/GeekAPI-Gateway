package cc.geektip.gateway.center.infrastructure.repository;

import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationSystemVO;
import cc.geektip.gateway.center.domain.register.repositoy.IRegisterManageRepository;
import cc.geektip.gateway.center.infrastructure.dao.ApplicationInterfaceDao;
import cc.geektip.gateway.center.infrastructure.dao.ApplicationInterfaceMethodDao;
import cc.geektip.gateway.center.infrastructure.dao.ApplicationSystemDao;
import cc.geektip.gateway.center.infrastructure.po.ApplicationInterface;
import cc.geektip.gateway.center.infrastructure.po.ApplicationInterfaceMethod;
import cc.geektip.gateway.center.infrastructure.po.ApplicationSystem;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @description: 注册中心管理仓储服务实现类
 * @author: Fish
 * @date: 2024/5/29
 */
@Repository
public class RegisterManageRepository implements IRegisterManageRepository {

    @Resource
    private ApplicationSystemDao applicationSystemDao;

    @Resource
    private ApplicationInterfaceDao applicationInterfaceDao;

    @Resource
    private ApplicationInterfaceMethodDao applicationInterfaceMethodDao;

    @Override
    public void registerApplication(ApplicationSystemVO applicationSystemVO) {
        ApplicationSystem applicationSystem = new ApplicationSystem();
        applicationSystem.setSystemId(applicationSystemVO.getSystemId());
        applicationSystem.setSystemName(applicationSystemVO.getSystemName());
        applicationSystem.setSystemType(applicationSystemVO.getSystemType());
        applicationSystem.setSystemRegistry(applicationSystemVO.getSystemRegistry());
        boolean exist = applicationSystemDao.isExistBySystemId(applicationSystemVO.getSystemId());
        if (exist) {
            applicationSystemDao.updateApplicationSystem(applicationSystem);
        } else {
            applicationSystemDao.insert(applicationSystem);
        }
    }

    @Override
    public void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO) {
        ApplicationInterface applicationInterface = new ApplicationInterface();
        applicationInterface.setSystemId(applicationInterfaceVO.getSystemId());
        applicationInterface.setInterfaceId(applicationInterfaceVO.getInterfaceId());
        applicationInterface.setInterfaceName(applicationInterfaceVO.getInterfaceName());
        applicationInterface.setInterfaceVersion(applicationInterfaceVO.getInterfaceVersion());
        boolean exist = applicationInterfaceDao.isExistByInterfaceId(applicationInterface);
        if (exist) {
            applicationInterfaceDao.updateApplicationInterface(applicationInterface);
        } else {
            applicationInterfaceDao.insert(applicationInterface);
        }
    }

    @Override
    public void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO) {
        ApplicationInterfaceMethod applicationInterfaceMethod = new ApplicationInterfaceMethod();
        applicationInterfaceMethod.setSystemId(applicationInterfaceMethodVO.getSystemId());
        applicationInterfaceMethod.setInterfaceId(applicationInterfaceMethodVO.getInterfaceId());
        applicationInterfaceMethod.setMethodId(applicationInterfaceMethodVO.getMethodId());
        applicationInterfaceMethod.setMethodName(applicationInterfaceMethodVO.getMethodName());
        applicationInterfaceMethod.setParameterType(applicationInterfaceMethodVO.getParameterType());
        applicationInterfaceMethod.setUri(applicationInterfaceMethodVO.getUri());
        applicationInterfaceMethod.setHttpCommandType(applicationInterfaceMethodVO.getHttpCommandType());
        applicationInterfaceMethod.setAuth(applicationInterfaceMethodVO.getAuth());
        boolean exist = applicationInterfaceMethodDao.isExistByInterfaceMethodId(applicationInterfaceMethod);
        if (exist) {
            applicationInterfaceMethodDao.updateApplicationInterfaceMethod(applicationInterfaceMethod);
        } else {
            applicationInterfaceMethodDao.insert(applicationInterfaceMethod);
        }
    }

    @Override
    public boolean isExistBySystemId(String systemId) {
        return applicationSystemDao.isExistBySystemId(systemId);
    }

}
