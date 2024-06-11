package cc.geektip.gateway.center.domain.operation.service;

import cc.geektip.gateway.center.application.service.IOperationManageService;
import cc.geektip.gateway.center.domain.operation.model.vo.*;
import cc.geektip.gateway.center.domain.operation.repository.IOperationManageRepository;
import cc.geektip.gateway.center.infrastructure.common.OperationRequest;
import cc.geektip.gateway.center.infrastructure.common.OperationResult;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 管理运维服务
 * @author: Fish
 * @date: 2024/6/6
 */
@Service
public class OperationManageService implements IOperationManageService {

    @Resource
    private IOperationManageRepository operationManageRepository;

    @Override
    public OperationResult<GatewayServerDataVO> queryGatewayServer(OperationRequest<String> request) {
        List<GatewayServerDataVO> list = operationManageRepository.queryGatewayServerListByPage(request);
        int count = operationManageRepository.queryGatewayServerListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<ApplicationSystemDataVO> queryApplicationSystem(OperationRequest<ApplicationSystemDataVO> request) {
        List<ApplicationSystemDataVO> list = operationManageRepository.queryApplicationSystemListByPage(request);
        int count = operationManageRepository.queryApplicationSystemListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<ApplicationInterfaceDataVO> queryApplicationInterface(OperationRequest<ApplicationInterfaceDataVO> request) {
        List<ApplicationInterfaceDataVO> list = operationManageRepository.queryApplicationInterfaceListByPage(request);
        int count = operationManageRepository.queryApplicationInterfaceListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<ApplicationInterfaceMethodDataVO> queryApplicationInterfaceMethod(OperationRequest<ApplicationInterfaceMethodDataVO> request) {
        List<ApplicationInterfaceMethodDataVO> list = operationManageRepository.queryApplicationInterfaceMethodListByPage(request);
        int count = operationManageRepository.queryApplicationInterfaceMethodListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<GatewayServerDetailDataVO> queryGatewayServerDetail(OperationRequest<GatewayServerDetailDataVO> request) {
        List<GatewayServerDetailDataVO> list = operationManageRepository.queryGatewayServerDetailListByPage(request);
        int count = operationManageRepository.queryGatewayServerDetailListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<GatewayDistributionDataVO> queryGatewayDistribution(OperationRequest<GatewayDistributionDataVO> request) {
        List<GatewayDistributionDataVO> list = operationManageRepository.queryGatewayDistributionListByPage(request);
        int count = operationManageRepository.queryGatewayDistributionListCountByPage(request);
        return new OperationResult<>(count, list);
    }

}
