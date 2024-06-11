package cc.geektip.gateway.center.infrastructure.repository;

import cc.geektip.gateway.center.domain.manage.model.vo.*;
import cc.geektip.gateway.center.domain.manage.repository.IConfigManageRepository;
import cc.geektip.gateway.center.infrastructure.dao.*;
import cc.geektip.gateway.center.infrastructure.po.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: API 网关配置管理仓储服务实现类
 * @author: Fish
 * @date: 2024/5/28
 */
@Repository
public class ConfigManageRepository implements IConfigManageRepository {

    @Resource
    private GatewayServerDao gatewayServerDao;
    @Resource
    private GatewayServerDetailDao gatewayServerDetailDao;
    @Resource
    private GatewayDistributionDao gatewayDistributionDao;
    @Resource
    private ApplicationSystemDao applicationSystemDao;
    @Resource
    private ApplicationInterfaceDao applicationInterfaceDao;
    @Resource
    private ApplicationInterfaceMethodDao applicationInterfaceMethodDao;

    @Override
    public List<GatewayServerVO> queryGatewayServerList() {
        List<GatewayServer> gatewayServers = gatewayServerDao.selectList(null);
        return gatewayServers.stream().map(gatewayServer -> {
            GatewayServerVO gatewayServerVO = new GatewayServerVO();
            gatewayServerVO.setGroupId(gatewayServer.getGroupId());
            gatewayServerVO.setGroupName(gatewayServer.getGroupName());
            return gatewayServerVO;
        }).toList();
    }

    @Override
    public List<GatewayServerDetailVO> queryGatewayServerDetailList() {
        List<GatewayServerDetail> gatewayServerDetails = gatewayServerDetailDao.queryOnlineGatewayServerDetailList();
        return gatewayServerDetails.stream().map(gatewayServerDetail -> {
            GatewayServerDetailVO gatewayServerDetailVO = new GatewayServerDetailVO();
            gatewayServerDetailVO.setId(gatewayServerDetail.getId());
            gatewayServerDetailVO.setGroupId(gatewayServerDetail.getGroupId());
            gatewayServerDetailVO.setGatewayId(gatewayServerDetail.getGatewayId());
            gatewayServerDetailVO.setGatewayName(gatewayServerDetail.getGatewayName());
            gatewayServerDetailVO.setGatewayAddress(gatewayServerDetail.getGatewayAddress());
            gatewayServerDetailVO.setStatus(gatewayServerDetail.getStatus());
            gatewayServerDetailVO.setCreateTime(gatewayServerDetail.getCreateTime());
            gatewayServerDetailVO.setUpdateTime(gatewayServerDetail.getUpdateTime());
            return gatewayServerDetailVO;
        }).toList();
    }

    @Override
    public boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress, Integer status) {
        GatewayServerDetail gatewayServerDetail = new GatewayServerDetail();
        gatewayServerDetail.setGroupId(groupId);
        gatewayServerDetail.setGatewayId(gatewayId);
        gatewayServerDetail.setGatewayName(gatewayName);
        gatewayServerDetail.setGatewayAddress(gatewayAddress);
        gatewayServerDetail.setStatus(status);
        return gatewayServerDetailDao.insert(gatewayServerDetail) > 0;
    }

    @Override
    public GatewayServerDetailVO queryGatewayServerDetail(String gatewayId, String gatewayAddress) {
        GatewayServerDetail req = new GatewayServerDetail();
        req.setGatewayId(gatewayId);
        req.setGatewayAddress(gatewayAddress);
        GatewayServerDetail gatewayServerDetail = gatewayServerDetailDao.queryOnlineGatewayServerDetail(req);
        if (null == gatewayServerDetail) return null;
        GatewayServerDetailVO gatewayServerDetailVO = new GatewayServerDetailVO();
        gatewayServerDetailVO.setId(gatewayServerDetail.getId());
        gatewayServerDetailVO.setGroupId(gatewayServerDetail.getGroupId());
        gatewayServerDetailVO.setGatewayId(gatewayServerDetail.getGatewayId());
        gatewayServerDetailVO.setGatewayName(gatewayServerDetail.getGatewayName());
        gatewayServerDetailVO.setGatewayAddress(gatewayServerDetail.getGatewayAddress());
        gatewayServerDetailVO.setStatus(gatewayServerDetail.getStatus());
        gatewayServerDetailVO.setCreateTime(gatewayServerDetail.getCreateTime());
        gatewayServerDetailVO.setUpdateTime(gatewayServerDetail.getUpdateTime());
        return gatewayServerDetailVO;
    }

    @Override
    public boolean updateGatewayStatus(String gatewayId, String gatewayAddress, Integer available) {
        GatewayServerDetail gatewayServerDetail = new GatewayServerDetail();
        gatewayServerDetail.setGatewayId(gatewayId);
        gatewayServerDetail.setGatewayAddress(gatewayAddress);
        gatewayServerDetail.setStatus(available);
        return gatewayServerDetailDao.updateGatewayStatus(gatewayServerDetail);
    }

    @Override
    public List<String> queryGatewayDistributionSystemIdList(String gatewayId) {
        return gatewayDistributionDao.queryGatewayDistributionSystemIdList();
    }

    @Override
    public List<ApplicationSystemVO> queryApplicationSystemList(List<String> systemIdList) {
        List<ApplicationSystem> applicationSystemList = applicationSystemDao.queryApplicationSystemList(systemIdList);
        return applicationSystemList.stream().map(applicationSystem -> {
            ApplicationSystemVO applicationSystemVO = new ApplicationSystemVO();
            applicationSystemVO.setSystemId(applicationSystem.getSystemId());
            applicationSystemVO.setSystemName(applicationSystem.getSystemName());
            applicationSystemVO.setSystemType(applicationSystem.getSystemType());
            applicationSystemVO.setSystemRegistry(applicationSystem.getSystemRegistry());
            return applicationSystemVO;
        }).toList();
    }

    @Override
    public List<ApplicationInterfaceVO> queryApplicationInterfaceList(String systemId) {
        List<ApplicationInterface> applicationInterfaces = applicationInterfaceDao.queryApplicationInterfaceList(systemId);
        return applicationInterfaces.stream().map(applicationInterface -> {
            ApplicationInterfaceVO applicationInterfaceVO = new ApplicationInterfaceVO();
            applicationInterfaceVO.setSystemId(applicationInterface.getSystemId());
            applicationInterfaceVO.setInterfaceId(applicationInterface.getInterfaceId());
            applicationInterfaceVO.setInterfaceName(applicationInterface.getInterfaceName());
            applicationInterfaceVO.setInterfaceVersion(applicationInterface.getInterfaceVersion());
            return applicationInterfaceVO;
        }).toList();
    }

    @Override
    public List<ApplicationInterfaceMethodVO> queryApplicationInterfaceMethodList(String systemId, String interfaceId) {
        ApplicationInterfaceMethod req = new ApplicationInterfaceMethod();
        req.setSystemId(systemId);
        req.setInterfaceId(interfaceId);
        List<ApplicationInterfaceMethod> applicationInterfaceMethods = applicationInterfaceMethodDao.queryApplicationInterfaceMethodList(req);
        return applicationInterfaceMethods.stream().map(applicationInterfaceMethod -> {
            ApplicationInterfaceMethodVO applicationInterfaceMethodVO = new ApplicationInterfaceMethodVO();
            applicationInterfaceMethodVO.setSystemId(applicationInterfaceMethod.getSystemId());
            applicationInterfaceMethodVO.setInterfaceId(applicationInterfaceMethod.getInterfaceId());
            applicationInterfaceMethodVO.setMethodId(applicationInterfaceMethod.getMethodId());
            applicationInterfaceMethodVO.setMethodName(applicationInterfaceMethod.getMethodName());
            applicationInterfaceMethodVO.setParameterType(applicationInterfaceMethod.getParameterType());
            applicationInterfaceMethodVO.setUri(applicationInterfaceMethod.getUri());
            applicationInterfaceMethodVO.setHttpCommandType(applicationInterfaceMethod.getHttpCommandType());
            applicationInterfaceMethodVO.setAuth(applicationInterfaceMethod.getAuth());
            return applicationInterfaceMethodVO;
        }).toList();
    }

    @Override
    public String queryGatewayDistribution(String systemId) {
        return gatewayDistributionDao.queryGatewayDistribution(systemId);
    }

    @Override
    public List<GatewayDistributionVO> queryGatewayDistributionList() {
        List<GatewayDistribution> gatewayDistributions = gatewayDistributionDao.selectList(null);
        return gatewayDistributions.stream().map(gatewayDistribution -> {
            GatewayDistributionVO gatewayDistributionVO = new GatewayDistributionVO();
            gatewayDistributionVO.setId(gatewayDistribution.getId());
            gatewayDistributionVO.setGroupId(gatewayDistribution.getGroupId());
            gatewayDistributionVO.setGatewayId(gatewayDistribution.getGatewayId());
            gatewayDistributionVO.setSystemId(gatewayDistribution.getSystemId());
            gatewayDistributionVO.setSystemName(gatewayDistribution.getSystemName());
            gatewayDistributionVO.setCreateTime(gatewayDistribution.getCreateTime());
            gatewayDistributionVO.setUpdateTime(gatewayDistribution.getUpdateTime());
            return gatewayDistributionVO;
        }).toList();
    }

    @Override
    public void distributionGatewayServerNode(String groupId, String gatewayId, String systemId, String systemName) {
        GatewayDistribution gatewayDistribution = new GatewayDistribution();
        gatewayDistribution.setGroupId(groupId);
        gatewayDistribution.setGatewayId(gatewayId);
        gatewayDistribution.setSystemId(systemId);
        gatewayDistribution.setSystemName(systemName);
        gatewayDistributionDao.insert(gatewayDistribution);
    }

    @Override
    public String queryApplicationSystemName(String systemId) {
        return applicationSystemDao.queryApplicationSystemName(systemId);
    }

}