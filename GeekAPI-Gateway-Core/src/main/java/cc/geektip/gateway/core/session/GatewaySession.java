package cc.geektip.gateway.core.session;

import cc.geektip.gateway.core.bind.IGenericReference;

import java.util.Map;

/**
 * @description: 网关会话
 * @author: Fish
 * @date: 2024/5/25
 */
public interface GatewaySession {

    /**
     * GET请求
     * @param methodName 方法名
     * @param params 参数
     * @return 结果
     */
    Object get(String methodName, Map<String, Object> params);

    /**
     * POST请求
     * @param methodName 方法名
     * @param params 参数
     * @return 结果
     */
    Object post(String methodName, Map<String, Object> params);

    /**
     * 获取映射器
     * @return 映射器
     */
    IGenericReference getMapper();

    /**
     * 获取配置
     * @return 配置
     */
    Configuration getConfiguration();

}
