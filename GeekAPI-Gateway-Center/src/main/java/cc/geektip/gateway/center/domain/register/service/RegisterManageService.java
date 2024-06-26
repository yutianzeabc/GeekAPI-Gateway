package cc.geektip.gateway.center.domain.register.service;

import cc.geektip.gateway.center.application.service.IRegisterManageService;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationSystemVO;
import cc.geektip.gateway.center.domain.register.repositoy.IRegisterManageRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 接口注册服务
 * @author: Fish
 * @date: 2024/5/29
 */
@Service
public class RegisterManageService implements IRegisterManageService {

    @Resource
    private IRegisterManageRepository registerManageRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerApplication(ApplicationSystemVO applicationSystemVO) {
        registerManageRepository.registerApplication(applicationSystemVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO) {
        registerManageRepository.registerApplicationInterface(applicationInterfaceVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO) {
        registerManageRepository.registerApplicationInterfaceMethod(applicationInterfaceMethodVO);
    }

}
