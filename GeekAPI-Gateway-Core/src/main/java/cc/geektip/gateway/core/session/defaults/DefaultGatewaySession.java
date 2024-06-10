package cc.geektip.gateway.core.session.defaults;

import cc.geektip.gateway.core.bind.IGenericReference;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.executor.Executor;
import cc.geektip.gateway.core.mapping.HttpStatement;
import cc.geektip.gateway.core.session.GatewaySession;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * @description: 默认网关会话实现
 * @author: Fish
 * @date: 2024/5/25
 */
@RequiredArgsConstructor
public class DefaultGatewaySession implements GatewaySession {

    private final Configuration configuration;

    private final String uri;

    private final Executor executor;

    @Override
    public Object get(String methodName, Map<String, Object> params) {
        HttpStatement httpStatement = configuration.getHttpStatement(uri);
        try {
            return executor.exec(httpStatement, params);
        } catch (Exception e) {
            throw new RuntimeException("Error exec get. Cause: " + e);
        }
    }

    @Override
    public Object post(String methodName, Map<String, Object> params) {
        return get(methodName, params);
    }

    @Override
    public IGenericReference getMapper() {
        return configuration.getMapper(uri, this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

}
