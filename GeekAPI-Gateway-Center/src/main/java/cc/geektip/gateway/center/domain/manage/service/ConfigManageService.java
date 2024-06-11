package cc.geektip.gateway.center.domain.manage.service;

import cc.geektip.gateway.center.application.service.IConfigManageService;
import cc.geektip.gateway.center.domain.manage.aggregates.ApplicationSystemRichInfo;
import cc.geektip.gateway.center.domain.manage.model.vo.*;
import cc.geektip.gateway.center.domain.manage.repository.IConfigManageRepository;
import cc.geektip.gateway.center.infrastructure.common.Constants;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: API 服务实现
 * @author: Fish
 * @date: 2024/5/28
 */
@Service
public class ConfigManageService implements IConfigManageService {

    @Resource
    private IConfigManageRepository configManageRepository;

    @Override
    public List<GatewayServerVO> queryGatewayServerList() {
        return configManageRepository.queryGatewayServerList();
    }

    @Override
    public List<GatewayServerDetailVO> queryGatewayServerDetailList() {
        return configManageRepository.queryGatewayServerDetailList();
    }

    @Override
    public List<GatewayDistributionVO> queryGatewayDistributionList() {
        return configManageRepository.queryGatewayDistributionList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        GatewayServerDetailVO gatewayServerDetailVO = configManageRepository.queryGatewayServerDetail(gatewayId);
        if (null == gatewayServerDetailVO) {
            try {
                return configManageRepository.registerGatewayServerNode(groupId, gatewayId, gatewayName, gatewayAddress, Constants.GatewayStatus.Available);
            } catch (DuplicateKeyException e) {
                return configManageRepository.updateGatewayStatus(gatewayId, gatewayAddress, Constants.GatewayStatus.Available);
            }
        } else {
            return configManageRepository.updateGatewayStatus(gatewayId, gatewayAddress, Constants.GatewayStatus.Available);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplicationSystemRichInfo queryApplicationSystemRichInfo(String gatewayId, String systemId) {
        // 1. 查询出网关ID对应的关联系统ID集合。也就是一个网关ID会被分配一些系统RPC服务注册进来，需要把这些服务查询出来。
        List<String> systemIdList = new ArrayList<>();
        if (StringUtils.hasText(systemId)){
            systemIdList.add(systemId);
        } else if (StringUtils.hasText(gatewayId)) {
            systemIdList = configManageRepository.queryGatewayDistributionSystemIdList(gatewayId);
        } else {
            throw new IllegalArgumentException("网关ID和系统ID不能同时为空");
        }
        // 2. 查询系统ID对应的系统列表信息
        List<ApplicationSystemVO> applicationSystemVOList = configManageRepository.queryApplicationSystemList(systemIdList);
        // 3. 查询系统下的接口信息
        for (ApplicationSystemVO applicationSystemVO : applicationSystemVOList) {
            List<ApplicationInterfaceVO> applicationInterfaceVOList = configManageRepository.queryApplicationInterfaceList(applicationSystemVO.getSystemId());
            for (ApplicationInterfaceVO applicationInterfaceVO : applicationInterfaceVOList) {
                List<ApplicationInterfaceMethodVO> applicationInterfaceMethodVOList = configManageRepository.queryApplicationInterfaceMethodList(applicationSystemVO.getSystemId(), applicationInterfaceVO.getInterfaceId());
                applicationInterfaceVO.setMethodList(applicationInterfaceMethodVOList);
            }
            applicationSystemVO.setInterfaceList(applicationInterfaceVOList);
        }
        return new ApplicationSystemRichInfo(gatewayId, applicationSystemVOList);
    }

    @Override
    public String queryGatewayDistribution(String systemId) {
        return configManageRepository.queryGatewayDistribution(systemId);
    }

    @Override
    public List<ApplicationSystemVO> queryApplicationSystemList() {
        return configManageRepository.queryApplicationSystemList(null);
    }

    @Override
    public List<ApplicationInterfaceVO> queryApplicationInterfaceList() {
        return configManageRepository.queryApplicationInterfaceList(null);
    }

    @Override
    public List<ApplicationInterfaceMethodVO> queryApplicationInterfaceMethodList() {
        return configManageRepository.queryApplicationInterfaceMethodList(null, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void distributionGatewayServerNode(String groupId, String gatewayId, String systemId) {
        String systemName = configManageRepository.queryApplicationSystemName(systemId);
        if (!StringUtils.hasText(systemName))
            throw new RuntimeException("网关算力与系统挂载配置失败，systemId：" + systemId + " 在 application_system 中不存在！");
        configManageRepository.distributionGatewayServerNode(groupId, gatewayId, systemId, systemName);
    }

}