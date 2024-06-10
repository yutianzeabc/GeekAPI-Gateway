package cc.geektip.gateway.center.interfaces;

import cc.geektip.gateway.center.application.IConfigManageService;
import cc.geektip.gateway.center.application.IMessageService;
import cc.geektip.gateway.center.domain.loadbalance.model.event.GatewayServerRegisteredEvent;
import cc.geektip.gateway.center.domain.manage.aggregates.ApplicationSystemRichInfo;
import cc.geektip.gateway.center.domain.manage.model.vo.*;
import cc.geektip.gateway.center.infrastructure.common.ResponseCode;
import cc.geektip.gateway.center.infrastructure.common.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description: 网关配置管理接口
 * @author: Fish
 * @date: 2024/5/28
 */
@Slf4j
@RestController
@RequestMapping("/wg/admin/config")
public class ConfigManageController {

    @Resource
    private IConfigManageService configManageService;

    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Resource
    private IMessageService messageService;

    /**
     * 查询网关服务配置项信息
     *
     * @return 网关服务配置项信息
     */
    @GetMapping(value = "queryServerConfig")
    public Result<List<GatewayServerVO>> queryServerConfig() {
        try {
            log.info("查询网关服务配置项信息");
            List<GatewayServerVO> gatewayServerVOList = configManageService.queryGatewayServerList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("查询网关服务配置项信息异常：", e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @GetMapping(value = "queryServerDetailConfig")
    public Result<List<GatewayServerDetailVO>> queryServerDetailConfig() {
        try {
            log.info("查询网关算力节点配置项信息");
            List<GatewayServerDetailVO> gatewayServerVOList = configManageService.queryGatewayServerDetailList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("查询网关算力节点配置项信息异常", e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @GetMapping(value = "queryGatewayDistributionList")
    public Result<List<GatewayDistributionVO>> queryGatewayDistributionList() {
        try {
            log.info("查询网关分配配置项信息");
            List<GatewayDistributionVO> gatewayServerVOList = configManageService.queryGatewayDistributionList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("查询网关分配配置项信息异常", e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    /**
     * 注册网关服务节点
     *
     * @param groupId        分组标识
     * @param gatewayId      网关标识
     * @param gatewayName    网关名称
     * @param gatewayAddress 网关地址
     * @return 注册状态
     */
    @PostMapping(value = "registerGateway")
    public Result<Boolean> registerGatewayServerNode(@RequestParam String groupId, @RequestParam String gatewayId, @RequestParam String gatewayName, @RequestParam String gatewayAddress) {
        try {
            log.info("注册网关服务节点 gatewayId：{} gatewayName：{} gatewayAddress：{}", gatewayId, gatewayName, gatewayAddress);
            // 1. 注册网关服务节点
            boolean done = configManageService.registerGatewayServerNode(groupId, gatewayId, gatewayName, gatewayAddress);
            // 2. 读取最新网关算力数据
            List<GatewayServerDetailVO> gatewayServerDetailVOList = configManageService.queryGatewayServerDetailList();
            // 3. 发布网关服务节点注册事件，触发Caddy配置刷新
            eventPublisher.publishEvent(new GatewayServerRegisteredEvent(this, gatewayServerDetailVOList));
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), done);
        } catch (Exception e) {
            log.error("注册网关服务节点异常：", e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), false);
        }
    }

    /**
     * TODO
     */
    @PostMapping(value = "distributionGateway")
    public void distributionGatewayServerNode(@RequestParam String groupId, @RequestParam String gatewayId) {

    }

    @PostMapping(value = "queryApplicationSystemList")
    public Result<List<ApplicationSystemVO>> queryApplicationSystemList() {
        try {
            log.info("查询应用服务配置项信息");
            List<ApplicationSystemVO> gatewayServerVOList = configManageService.queryApplicationSystemList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("查询应用服务配置项信息异常", e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), null);
        }
    }
    @PostMapping(value = "queryApplicationInterfaceList")
    public Result<List<ApplicationInterfaceVO>> queryApplicationInterfaceList() {
        try {
            log.info("查询应用接口配置项信息");
            List<ApplicationInterfaceVO> gatewayServerVOList = configManageService.queryApplicationInterfaceList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("查询应用接口配置项信息异常", e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "queryApplicationInterfaceMethodList")
    public Result<List<ApplicationInterfaceMethodVO>> queryApplicationInterfaceMethodList() {
        try {
            log.info("查询应用接口方法配置项信息");
            List<ApplicationInterfaceMethodVO> gatewayServerVOList = configManageService.queryApplicationInterfaceMethodList();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), gatewayServerVOList);
        } catch (Exception e) {
            log.error("查询应用接口方法配置项信息异常", e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "queryApplicationSystemRichInfo")
    public Result<ApplicationSystemRichInfo> queryApplicationSystemRichInfo(@RequestParam String gatewayId, @RequestParam String systemId) {
        try {
            log.info("查询分配到网关下的待注册系统信息(系统、接口、方法) gatewayId：{}", gatewayId);
            ApplicationSystemRichInfo applicationSystemRichInfo = configManageService.queryApplicationSystemRichInfo(gatewayId, systemId);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), applicationSystemRichInfo);
        } catch (IllegalArgumentException e) {
            log.error("查询分配到网关下的待注册系统信息(系统、接口、方法)异常 gatewayId：{}", gatewayId, e);
            return new Result<>(ResponseCode.ILLEGAL_PARAMETER.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            log.error("查询分配到网关下的待注册系统信息(系统、接口、方法)异常 gatewayId：{}", gatewayId, e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), null);
        }
    }

    @PostMapping(value = "queryRedisConfig")
    public Result<Map<String, String>> queryRedisConfig() {
        try {
            log.info("查询配置中心Redis配置信息");
            Map<String, String> redisConfig = messageService.queryRedisConfig();
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), redisConfig);
        } catch (Exception e) {
            log.error("查询配置中心Redis配置信息异常：", e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), null);
        }
    }

}
