package cc.geektip.gateway.core.mapping;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @description: HTTP 指令
 * @author: Fish
 * @date: 2024/5/25
 */
@Getter
@RequiredArgsConstructor
public class HttpStatement {

    /**
     * 应用
     */
    private final String application;
    /**
     * 服务接口
     */
    private final String interfaceName;
    /**
     * 服务方法
     */
    private final String methodName;
    /**
     * 参数类型 (RPC限制单参数注册)
     */
    private final String parameterType;
    /**
     * 网关接口
     */
    private final String uri;
    /**
     * 指令类型
     */
    private final HttpCommandType httpCommandType;

    /**
     * 是否鉴权
     */
    private final boolean auth;
}
