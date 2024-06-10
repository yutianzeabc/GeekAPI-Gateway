package cc.geektip.gateway.core.executor;

import cc.geektip.gateway.core.datasource.Connection;
import cc.geektip.gateway.core.mapping.HttpStatement;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.type.SimpleTypeRegistry;
import cc.geektip.gateway.core.executor.result.SessionResult;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @description: 执行器基类
 * @author: Fish
 * @date: 2024/5/26
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseExecutor implements Executor {

    protected Configuration configuration;
    protected Connection connection;

    public BaseExecutor(Configuration configuration, Connection connection) {
        this.configuration = configuration;
        this.connection = connection;
    }

    @Override
    public SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception {
        // 参数处理
        String methodName = httpStatement.getMethodName();
        String parameterType = httpStatement.getParameterType();
        String[] parameterTypes = new String[]{parameterType};
        Object[] args = SimpleTypeRegistry.isSimpleType(parameterType) ? params.values().toArray() : new Object[]{params};
        log.info("Call Method: {}#{}.{}({}) args：{}", httpStatement.getApplication(), httpStatement.getInterfaceName(), httpStatement.getMethodName(), JSON.toJSONString(parameterTypes), JSON.toJSONString(args));
        // 调用抽象方法
        try {
            Object data = doExec(methodName, parameterTypes, args);
            return SessionResult.buildSuccess(data);
        } catch (Exception e) {
            return SessionResult.buildError(e.getMessage());
        }
    }

    protected abstract Object doExec(String methodName, String[] parameterTypes, Object[] args);

}
