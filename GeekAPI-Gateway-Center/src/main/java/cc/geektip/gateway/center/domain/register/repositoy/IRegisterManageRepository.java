package cc.geektip.gateway.center.domain.register.repositoy;

import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationSystemVO;

/**
 * @description: 注册管理仓储服务
 * @author: Fish
 * @date: 2024/5/29
 */
public interface IRegisterManageRepository {

    /**
     * 注册应用
     * @param applicationSystemVO 应用系统 VO 对象
     */
    void registerApplication(ApplicationSystemVO applicationSystemVO);

    /**
     * 注册应用接口
     * @param applicationInterfaceVO 应用接口 VO 对象
     */
    void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO);

    /**
     * 注册应用接口方法
     * @param applicationInterfaceMethodVO
     */
    void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO);

}
