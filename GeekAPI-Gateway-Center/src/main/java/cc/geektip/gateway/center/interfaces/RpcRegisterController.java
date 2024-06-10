package cc.geektip.gateway.center.interfaces;

import cc.geektip.gateway.center.application.IConfigManageService;
import cc.geektip.gateway.center.application.IMessageService;
import cc.geektip.gateway.center.application.IRegisterManageService;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import cc.geektip.gateway.center.domain.register.model.vo.ApplicationSystemVO;
import cc.geektip.gateway.center.infrastructure.common.ResponseCode;
import cc.geektip.gateway.center.infrastructure.common.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: API网关服务注册接口
 * @author: Fish
 * @date: 2024/5/29
 */
@Slf4j
@RestController
@RequestMapping("/wg/admin/register")
public class RpcRegisterController {

    @Resource
    private IRegisterManageService registerManageService;
    @Resource
    private IConfigManageService configManageService;
    @Resource
    private IMessageService messageService;


    @PostMapping(value = "registerApplication")
    public Result<Boolean> registerApplication(@RequestParam String systemId,
                                               @RequestParam String systemName,
                                               @RequestParam String systemType,
                                               @RequestParam String systemRegistry) {
        try {
            log.info("注册应用服务 systemId：{}", systemId);
            ApplicationSystemVO applicationSystemVO = new ApplicationSystemVO();
            applicationSystemVO.setSystemId(systemId);
            applicationSystemVO.setSystemName(systemName);
            applicationSystemVO.setSystemType(systemType);
            applicationSystemVO.setSystemRegistry(systemRegistry);
            registerManageService.registerApplication(applicationSystemVO);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        } catch (DuplicateKeyException e) {
            log.warn("注册应用服务重复 systemId：{}", systemId);
            return new Result<>(ResponseCode.INDEX_DUP.getCode(), e.getMessage(), true);
        } catch (Exception e) {
            log.error("注册应用服务失败 systemId：{}", systemId, e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), false);
        }
    }

    @PostMapping(value = "registerApplicationInterface")
    public Result<Boolean> registerApplicationInterface(@RequestParam String systemId,
                                                        @RequestParam String interfaceId,
                                                        @RequestParam String interfaceName,
                                                        @RequestParam String interfaceVersion) {
        try {
            log.info("注册应用接口 systemId：{} interfaceId：{}", systemId, interfaceId);
            ApplicationInterfaceVO applicationInterfaceVO = new ApplicationInterfaceVO();
            applicationInterfaceVO.setSystemId(systemId);
            applicationInterfaceVO.setInterfaceId(interfaceId);
            applicationInterfaceVO.setInterfaceName(interfaceName);
            applicationInterfaceVO.setInterfaceVersion(interfaceVersion);
            registerManageService.registerApplicationInterface(applicationInterfaceVO);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        } catch (DuplicateKeyException e) {
            log.warn("注册应用接口重复 systemId：{} interfaceId：{}", systemId, interfaceId);
            return new Result<>(ResponseCode.INDEX_DUP.getCode(), e.getMessage(), true);
        } catch (Exception e) {
            log.error("注册应用接口失败 systemId：{}", systemId, e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), false);
        }
    }

    @PostMapping(value = "registerApplicationInterfaceMethod")
    public Result<Boolean> registerApplicationInterfaceMethod(@RequestParam String systemId,
                                                              @RequestParam String interfaceId,
                                                              @RequestParam String methodId,
                                                              @RequestParam String methodName,
                                                              @RequestParam String parameterType,
                                                              @RequestParam String uri,
                                                              @RequestParam String httpCommandType,
                                                              @RequestParam Integer auth) {
        try {
            log.info("注册应用接口方法 systemId：{} interfaceId：{} methodId：{}", systemId, interfaceId, methodId);
            ApplicationInterfaceMethodVO applicationInterfaceMethodVO = new ApplicationInterfaceMethodVO();
            applicationInterfaceMethodVO.setSystemId(systemId);
            applicationInterfaceMethodVO.setInterfaceId(interfaceId);
            applicationInterfaceMethodVO.setMethodId(methodId);
            applicationInterfaceMethodVO.setMethodName(methodName);
            applicationInterfaceMethodVO.setParameterType(parameterType);
            applicationInterfaceMethodVO.setUri(uri);
            applicationInterfaceMethodVO.setHttpCommandType(httpCommandType);
            applicationInterfaceMethodVO.setAuth(auth);
            registerManageService.registerApplicationInterfaceMethod(applicationInterfaceMethodVO);
            // 推送注册消息
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        } catch (DuplicateKeyException e) {
            log.warn("注册应用接口重复 systemId：{} interfaceId：{}", systemId, interfaceId);
            return new Result<>(ResponseCode.INDEX_DUP.getCode(), e.getMessage(), true);
        } catch (Exception e) {
            log.error("注册应用接口失败 systemId：{}", systemId, e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), false);
        }
    }

    @PostMapping(value = "registerEvent")
    public Result<Boolean> registerEvent(@RequestParam String systemId) {
        try {
            log.info("应用信息注册完成通知 systemId：{}", systemId);
            // 推送注册消息
            String gatewayId = configManageService.queryGatewayDistribution(systemId);
            messageService.pushMessage(gatewayId, systemId);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        } catch (Exception e) {
            log.error("应用信息注册完成通知失败 systemId：{}", systemId, e);
            return new Result<>(ResponseCode.UNKNOWN_ERROR.getCode(), e.getMessage(), false);
        }
    }

}
