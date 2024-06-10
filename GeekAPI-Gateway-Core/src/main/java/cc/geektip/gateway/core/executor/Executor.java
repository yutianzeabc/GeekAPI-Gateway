package cc.geektip.gateway.core.executor;

import cc.geektip.gateway.core.executor.result.SessionResult;
import cc.geektip.gateway.core.mapping.HttpStatement;

import java.util.Map;

/**
 * @description: 执行器
 * @author: Fish
 * @date: 2024/5/26
 */
public interface Executor {

    /**
     * 执行方法调用
     * @param httpStatement HTTP 指令
     * @param params 参数列表
     * @return 会话结果
     * @throws Exception 异常
     */
    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;

}
