package cc.geektip.gateway.center.infrastructure.repository;

import cc.geektip.gateway.center.domain.operation.model.vo.*;
import cc.geektip.gateway.center.domain.operation.repository.IOperationManageRepository;
import cc.geektip.gateway.center.infrastructure.common.OperationRequest;
import cc.geektip.gateway.center.infrastructure.dao.*;
import cc.geektip.gateway.center.infrastructure.po.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 运营管理仓储
 * @author: Fish
 * @date: 2024/6/5
 */
@Repository
public class OperationManageRepository implements IOperationManageRepository {

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
    public List<GatewayServerDataVO> queryGatewayServerListByPage(OperationRequest<String> request) {
        List<GatewayServer> gatewayServers = gatewayServerDao.queryGatewayServerListByPage(request);
        return gatewayServers.stream().map(gatewayServer -> {
            GatewayServerDataVO gatewayServerVO = new GatewayServerDataVO();
            gatewayServerVO.setId(gatewayServer.getId());
            gatewayServerVO.setGroupId(gatewayServer.getGroupId());
            gatewayServerVO.setGroupName(gatewayServer.getGroupName());
            return gatewayServerVO;
        }).toList();
    }

    @Override
    public int queryGatewayServerListCountByPage(OperationRequest<String> request) {
        return gatewayServerDao.queryGatewayServerListCountByPage(request);
    }

    @Override
    public List<ApplicationSystemDataVO> queryApplicationSystemListByPage(OperationRequest<ApplicationSystemDataVO> request) {
        List<ApplicationSystem> applicationSystems = applicationSystemDao.queryApplicationSystemListByPage(request);
        return applicationSystems.stream().map(applicationSystem -> {
            ApplicationSystemDataVO applicationSystemDataVO = new ApplicationSystemDataVO();
            applicationSystemDataVO.setSystemId(applicationSystem.getSystemId());
            applicationSystemDataVO.setSystemName(applicationSystem.getSystemName());
            applicationSystemDataVO.setSystemType(applicationSystem.getSystemType());
            applicationSystemDataVO.setSystemRegistry(applicationSystem.getSystemRegistry());
            return applicationSystemDataVO;
        }).toList();
    }

    @Override
    public int queryApplicationSystemListCountByPage(OperationRequest<ApplicationSystemDataVO> request) {
        return applicationSystemDao.queryApplicationSystemListCountByPage(request);
    }

    @Override
    public List<ApplicationInterfaceDataVO> queryApplicationInterfaceListByPage(OperationRequest<ApplicationInterfaceDataVO> request) {
        List<ApplicationInterface> applicationInterfaces = applicationInterfaceDao.queryApplicationInterfaceListByPage(request);
        return applicationInterfaces.stream().map(applicationInterface -> {
            ApplicationInterfaceDataVO applicationInterfaceDataVO = new ApplicationInterfaceDataVO();
            applicationInterfaceDataVO.setSystemId(applicationInterface.getSystemId());
            applicationInterfaceDataVO.setInterfaceId(applicationInterface.getInterfaceId());
            applicationInterfaceDataVO.setInterfaceName(applicationInterface.getInterfaceName());
            applicationInterfaceDataVO.setInterfaceVersion(applicationInterface.getInterfaceVersion());
            return applicationInterfaceDataVO;
        }).toList();
    }

    @Override
    public int queryApplicationInterfaceListCountByPage(OperationRequest<ApplicationInterfaceDataVO> request) {
        return applicationInterfaceDao.queryApplicationInterfaceListCountByPage(request);
    }

    @Override
    public List<ApplicationInterfaceMethodDataVO> queryApplicationInterfaceMethodListByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request) {
        List<ApplicationInterfaceMethod> applicationInterfaceMethods = applicationInterfaceMethodDao.queryApplicationInterfaceMethodListByPage(request);
        return applicationInterfaceMethods.stream().map(applicationInterfaceMethod -> {
            ApplicationInterfaceMethodDataVO applicationInterfaceMethodDataVO = new ApplicationInterfaceMethodDataVO();
            applicationInterfaceMethodDataVO.setSystemId(applicationInterfaceMethod.getSystemId());
            applicationInterfaceMethodDataVO.setInterfaceId(applicationInterfaceMethod.getInterfaceId());
            applicationInterfaceMethodDataVO.setMethodId(applicationInterfaceMethod.getMethodId());
            applicationInterfaceMethodDataVO.setMethodName(applicationInterfaceMethod.getMethodName());
            applicationInterfaceMethodDataVO.setParameterType(applicationInterfaceMethod.getParameterType());
            applicationInterfaceMethodDataVO.setUri(applicationInterfaceMethod.getUri());
            applicationInterfaceMethodDataVO.setHttpCommandType(applicationInterfaceMethod.getHttpCommandType());
            applicationInterfaceMethodDataVO.setAuth(applicationInterfaceMethod.getAuth());
            return applicationInterfaceMethodDataVO;
        }).toList();
    }

    @Override
    public int queryApplicationInterfaceMethodListCountByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request) {
        return applicationInterfaceMethodDao.queryApplicationInterfaceMethodListCountByPage(request);
    }

    @Override
    public List<GatewayServerDetailDataVO> queryGatewayServerDetailListByPage(OperationRequest<GatewayServerDetailDataVO> request) {
        List<GatewayServerDetail> gatewayServerDetails = gatewayServerDetailDao.queryGatewayServerDetailListByPage(request);
        return gatewayServerDetails.stream().map(gatewayServerDetail -> {
            GatewayServerDetailDataVO gatewayServerDetailDataVO = new GatewayServerDetailDataVO();
            gatewayServerDetailDataVO.setId(gatewayServerDetail.getId());
            gatewayServerDetailDataVO.setGroupId(gatewayServerDetail.getGroupId());
            gatewayServerDetailDataVO.setGatewayId(gatewayServerDetail.getGatewayId());
            gatewayServerDetailDataVO.setGatewayName(gatewayServerDetail.getGatewayName());
            gatewayServerDetailDataVO.setGatewayAddress(gatewayServerDetail.getGatewayAddress());
            gatewayServerDetailDataVO.setStatus(gatewayServerDetail.getStatus());
            gatewayServerDetailDataVO.setCreateTime(gatewayServerDetail.getCreateTime());
            gatewayServerDetailDataVO.setUpdateTime(gatewayServerDetail.getUpdateTime());
            return gatewayServerDetailDataVO;
        }).toList();
    }

    @Override
    public int queryGatewayServerDetailListCountByPage(OperationRequest<GatewayServerDetailDataVO> request) {
        return gatewayServerDetailDao.queryGatewayServerDetailListCountByPage(request);
    }

    @Override
    public List<GatewayDistributionDataVO> queryGatewayDistributionListByPage(OperationRequest<GatewayDistributionDataVO> request) {
        List<GatewayDistribution> gatewayDistributions = gatewayDistributionDao.queryGatewayDistributionListByPage(request);
        return gatewayDistributions.stream().map(gatewayDistribution -> {
            GatewayDistributionDataVO gatewayDistributionDataVO = new GatewayDistributionDataVO();
            gatewayDistributionDataVO.setId(gatewayDistribution.getId());
            gatewayDistributionDataVO.setGroupId(gatewayDistribution.getGroupId());
            gatewayDistributionDataVO.setGatewayId(gatewayDistribution.getGatewayId());
            gatewayDistributionDataVO.setSystemId(gatewayDistribution.getSystemId());
            gatewayDistributionDataVO.setSystemName(gatewayDistribution.getSystemName());
            gatewayDistributionDataVO.setCreateTime(gatewayDistribution.getCreateTime());
            gatewayDistributionDataVO.setUpdateTime(gatewayDistribution.getUpdateTime());
            return gatewayDistributionDataVO;
        }).toList();
    }

    @Override
    public int queryGatewayDistributionListCountByPage(OperationRequest<GatewayDistributionDataVO> request) {
        return gatewayDistributionDao.queryGatewayDistributionListCountByPage(request);
    }

}
