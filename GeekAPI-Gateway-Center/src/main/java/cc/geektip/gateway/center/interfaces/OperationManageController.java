package cc.geektip.gateway.center.interfaces;

import cc.geektip.gateway.center.domain.operation.model.vo.*;
import cc.geektip.gateway.center.domain.operation.service.OperationManageService;
import cc.geektip.gateway.center.infrastructure.common.OperationRequest;
import cc.geektip.gateway.center.infrastructure.common.OperationResult;
import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 网关运维数据服务
 * @author: Fish
 * @date: 2024/6/6
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/wg/admin/data")
public class OperationManageController {

    @Resource
    private OperationManageService operationManageService;

    @GetMapping(value = "queryGatewayServer")
    public OperationResult<GatewayServerDataVO> queryGatewayServer(@RequestParam String groupId,
                                                                   @RequestParam String page,
                                                                   @RequestParam String limit) {
        try {
            log.info("查询网关服务数据开始 groupId：{} page：{} limit：{}", groupId, page, limit);
            OperationRequest<String> req = new OperationRequest<>(page, limit);
            req.setData(groupId);
            OperationResult<GatewayServerDataVO> operationResult = operationManageService.queryGatewayServer(req);
            log.info("查询网关服务数据完成 operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("查询网关服务数据异常 groupId：{}", groupId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryGatewayServerDetail")
    public OperationResult<GatewayServerDetailDataVO> queryGatewayServerDetail(@RequestParam String groupId,
                                                                               @RequestParam String gatewayId,
                                                                               @RequestParam String page,
                                                                               @RequestParam String limit) {
        try {
            log.info("查询网关服务详情数据开始 groupId：{} gatewayId：{} page：{} limit：{}", groupId, gatewayId, page, limit);
            OperationRequest<GatewayServerDetailDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new GatewayServerDetailDataVO(groupId, gatewayId));
            OperationResult<GatewayServerDetailDataVO> operationResult = operationManageService.queryGatewayServerDetail(req);
            log.info("查询网关服务详情数据完成 operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("查询网关服务详情数据异常 groupId：{}", groupId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryGatewayDistribution")
    public OperationResult<GatewayDistributionDataVO> queryGatewayDistribution(@RequestParam String groupId,
                                                                               @RequestParam String gatewayId,
                                                                               @RequestParam String page,
                                                                               @RequestParam String limit) {
        try {
            log.info("查询网关分配数据开始 groupId：{} gatewayId：{} page：{} limit：{}", groupId, gatewayId, page, limit);
            OperationRequest<GatewayDistributionDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new GatewayDistributionDataVO(groupId, gatewayId));
            OperationResult<GatewayDistributionDataVO> operationResult = operationManageService.queryGatewayDistribution(req);
            log.info("查询网关分配数据完成 operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("查询网关分配数据异常 groupId：{}", groupId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryApplicationSystem")
    public OperationResult<ApplicationSystemDataVO> queryApplicationSystem(@RequestParam String systemId,
                                                                           @RequestParam String systemName,
                                                                           @RequestParam String page,
                                                                           @RequestParam String limit) {
        try {
            log.info("查询应用系统数据开始 systemId：{} systemName：{} page：{} limit：{}", systemId, systemName, page, limit);
            OperationRequest<ApplicationSystemDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new ApplicationSystemDataVO(systemId, systemName));
            OperationResult<ApplicationSystemDataVO> operationResult = operationManageService.queryApplicationSystem(req);
            log.info("查询应用系统数据完成 operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("查询应用系统数据异常 systemId：{} systemName：{}", systemId, systemName, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryApplicationInterface")
    public OperationResult<ApplicationInterfaceDataVO> queryApplicationInterface(@RequestParam String systemId,
                                                                                 @RequestParam String interfaceId,
                                                                                 @RequestParam String page,
                                                                                 @RequestParam String limit) {
        try {
            log.info("查询应用接口信息开始 systemId：{} interfaceId：{} page：{} limit：{}", systemId, interfaceId, page, limit);
            OperationRequest<ApplicationInterfaceDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new ApplicationInterfaceDataVO(systemId, interfaceId));
            OperationResult<ApplicationInterfaceDataVO> operationResult = operationManageService.queryApplicationInterface(req);
            log.info("查询应用接口信息完成 operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("查询应用接口信息异常 systemId：{} interfaceId：{}", systemId, interfaceId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryApplicationInterfaceMethodList")
    public OperationResult<ApplicationInterfaceMethodDataVO> queryApplicationInterfaceMethodList(@RequestParam String systemId,
                                                                                                 @RequestParam String interfaceId,
                                                                                                 @RequestParam String page,
                                                                                                 @RequestParam String limit) {
        try {
            log.info("查询应用接口方法信息开始 systemId：{} interfaceId：{} page：{} limit：{}", systemId, interfaceId, page, limit);
            OperationRequest<ApplicationInterfaceMethodDataVO> req = new OperationRequest<>(page, limit);
            req.setData(new ApplicationInterfaceMethodDataVO(systemId, interfaceId));
            OperationResult<ApplicationInterfaceMethodDataVO> operationResult = operationManageService.queryApplicationInterfaceMethod(req);
            log.info("查询应用接口方法信息完成 operationResult：{}", JSON.toJSONString(operationResult));
            return operationResult;
        } catch (Exception e) {
            log.error("查询应用接口方法信息异常 systemId：{} interfaceId：{}", systemId, interfaceId, e);
            return new OperationResult<>(0, null);
        }
    }

}
