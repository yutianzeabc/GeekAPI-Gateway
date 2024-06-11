package cc.geektip.gateway.starter.domain.service;

import cc.geektip.gateway.starter.common.Result;
import cc.geektip.gateway.starter.domain.model.aggregates.ApplicationSystemRichInfo;
import cc.geektip.gateway.starter.exception.GatewayException;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 网关中心服务
 * @author: Fish
 * @date: 2024/5/30
 */
@Slf4j
public class GatewayCenterService {

    /**
     * 注册网关服务
     *
     * @param address        注册中心
     * @param groupId        分组ID
     * @param gatewayId      网关ID
     * @param gatewayName    网关名称
     * @param gatewayAddress 网关地址
     */
    public void doRegister(String address, String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupId", groupId);
        paramMap.put("gatewayId", gatewayId);
        paramMap.put("gatewayName", gatewayName);
        paramMap.put("gatewayAddress", gatewayAddress);
        String resultStr;
        Result<Boolean> result;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/config/registerGateway", paramMap, 2000);
            result = JSON.parseObject(resultStr, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("网关服务注册异常，链接资源不可用：{}", address + "/wg/admin/config/registerGateway");
            throw e;
        }
        if (null == result || !result.getData() || !"0000".equals(result.getCode()))
            throw new GatewayException("网关服务注册异常 [gatewayId：" + gatewayId + "] 、[gatewayAddress：" + gatewayAddress + "]");
        log.info("向网关中心注册网关算力服务 gatewayId：{} gatewayName：{} gatewayAddress：{} 注册结果：{}", gatewayId, gatewayName, gatewayAddress, resultStr);
    }

    /**
     * 注销网关服务
     *
     * @param address   注册中心
     * @param groupId   分组ID
     * @param gatewayId 网关ID
     */
    public void doOffline(String address, String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupId", groupId);
        paramMap.put("gatewayId", gatewayId);
        paramMap.put("gatewayName", gatewayName);
        paramMap.put("gatewayAddress", gatewayAddress);
        String resultStr;
        Result<Boolean> result;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/config/offlineGateway", paramMap, 2000);
            result = JSON.parseObject(resultStr, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("网关服务下线异常，链接资源不可用：{}", address + "/wg/admin/config/offlineGateway");
            throw e;
        }
        if (null == result || !result.getData() || !"0000".equals(result.getCode()))
            throw new GatewayException("网关服务下线异常 [gatewayId：" + gatewayId + "] 、[gatewayAddress：" + gatewayAddress + "]");
        log.info("向网关中心下线网关算力服务 gatewayId：{} gatewayName：{} gatewayAddress：{} 下线结果：{}", gatewayId, gatewayName, gatewayAddress, resultStr);
    }

    /**
     * 从网关中心拉取应用服务和接口的配置信息到本地完成注册
     *
     * @param address   注册中心
     * @param gatewayId 网关ID
     * @return 应用配置信息聚合
     */
    public ApplicationSystemRichInfo pullApplicationSystemRichInfo(String address, String gatewayId, String systemId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gatewayId", gatewayId);
        paramMap.put("systemId", systemId);
        String resultStr;
        Result<ApplicationSystemRichInfo> result;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/config/queryApplicationSystemRichInfo", paramMap, 2000);
            result = JSON.parseObject(resultStr, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("网关服务拉取异常，链接资源不可用：{}", address + "/wg/admin/config/queryApplicationSystemRichInfo");
            throw e;
        }
        if (null == result || !"0000".equals(result.getCode()))
            throw new GatewayException("从网关中心拉取应用服务和接口的配置信息到本地完成注册异常 [gatewayId：" + gatewayId + "]");
        log.info("从网关中心拉取应用服务和接口的配置信息到本地完成注册 [gatewayId：{}]", gatewayId);
        return result.getData();
    }

    /**
     * 从网关中心拉取Redis配置信息
     *
     * @param address 注册中心
     * @return Redis配置信息
     */
    public Map<String, String> queryRedisConfig(String address) {
        String resultStr;
        Result<Map<String, String>> result;
        try {
            resultStr = HttpUtil.post(address + "/wg/admin/config/queryRedisConfig", "", 2000);
            result = JSON.parseObject(resultStr, new TypeReference<>() {
            });
        } catch (Exception e) {
            log.error("网关服务拉取配置异常，链接资源不可用：{}", address + "/wg/admin/config/queryRedisConfig", e);
            throw e;
        }
        if (null == result || !"0000".equals(result.getCode()))
            throw new GatewayException("从网关中心拉取Redis配置信息异常");
        log.info("从网关中心拉取Redis配置信息：{}", resultStr);
        return result.getData();
    }

}
