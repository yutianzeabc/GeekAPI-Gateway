package cc.geektip.gateway.sdk.domain.service;

import cc.geektip.gateway.sdk.common.Result;
import cc.geektip.gateway.sdk.exception.GatewayException;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 网关中心服务
 * @author: Fish
 * @date: 2024/6/4
 */
@Slf4j
public class GatewayCenterService {

    public void doRegisterApplication(String address, String systemId, String systemName, String systemType, String systemRegistry) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        paramMap.put("systemName", systemName);
        paramMap.put("systemType", systemType);
        paramMap.put("systemRegistry", systemRegistry);
        String resultStr;
        Result<Boolean> result;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/register/registerApplication", paramMap, 2000);
            result = JSON.parseObject(resultStr, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("应用服务注册异常，链接资源不可用：{}", address + "/wg/admin/register/registerApplication");
            throw e;
        }
        log.info("向网关中心注册应用服务 systemId：{} systemName：{} 注册结果：{}", systemId, systemName, resultStr);
        if (null == result || !"0000".equals(result.getCode()) && !"0003".equals(result.getCode()))
            throw new GatewayException("注册应用服务异常 [systemId：" + systemId + "] 、[systemRegistry：" + systemRegistry + "]");
    }

    public void doRegisterApplicationInterface(String address, String systemId, String interfaceId, String interfaceName, String interfaceVersion) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        paramMap.put("interfaceId", interfaceId);
        paramMap.put("interfaceName", interfaceName);
        paramMap.put("interfaceVersion", interfaceVersion);
        String resultStr;
        Result<Boolean> result;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/register/registerApplicationInterface", paramMap, 2000);
            result = JSON.parseObject(resultStr, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("应用服务接口注册异常，链接资源不可用：{}", address + "/wg/admin/register/registerApplicationInterface");
            throw e;
        }
        log.info("向网关中心注册应用接口服务 systemId：{} interfaceId：{} interfaceName：{} 注册结果：{}", systemId, interfaceId, interfaceName, resultStr);
        if (null == result || !"0000".equals(result.getCode()) && !"0003".equals(result.getCode()))
            throw new GatewayException("注册应用接口服务异常 [systemId：" + systemId + "] 、[interfaceId：" + interfaceId + "]");
    }

    public void doRegisterApplicationInterfaceMethod(String address, String systemId, String interfaceId, String methodId, String methodName, String parameterType, String uri, String httpCommandType, Integer auth) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        paramMap.put("interfaceId", interfaceId);
        paramMap.put("methodId", methodId);
        paramMap.put("methodName", methodName);
        paramMap.put("parameterType", parameterType);
        paramMap.put("uri", uri);
        paramMap.put("httpCommandType", httpCommandType);
        paramMap.put("auth", auth);
        String resultStr;
        Result<Boolean> result;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/register/registerApplicationInterfaceMethod", paramMap, 2000);
            result = JSON.parseObject(resultStr, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("应用服务接口方法注册异常，链接资源不可用：{}", address + "/wg/admin/register/registerApplicationInterfaceMethod");
            throw e;
        }
        log.info("向网关中心注册应用接口方法 systemId：{} interfaceId：{} methodId：{} methodName：{} 注册结果：{}", systemId, interfaceId, methodId, methodName, resultStr);
        if (null == result || !"0000".equals(result.getCode()) && !"0003".equals(result.getCode()))
            throw new GatewayException("注册应用接口方法异常 [systemId：" + systemId + "] 、[interfaceId：" + interfaceId + "] 、[methodId：" + methodId + "]");
    }

    public void doPublishRegisterEvent(String address, String systemId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        String resultStr;
        Result<Boolean> result;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/register/registerEvent", paramMap, 2000);
            result = JSON.parseObject(resultStr, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("发布应用服务注册事件异常，链接资源不可用：{}", address + "/wg/admin/register/registerEvent");
            throw e;
        }
        log.info("向网关中心发布应用服务注册事件 systemId：{} 注册结果：{}", systemId, resultStr);
        if (null == result || !"0000".equals(result.getCode()) && !"0003".equals(result.getCode()))
            throw new GatewayException("发布应用服务注册事件异常 [systemId：" + systemId + "]");
    }

}
