package cc.geektip.gateway.core.executor;

import cc.geektip.gateway.core.datasource.Connection;
import cc.geektip.gateway.core.session.Configuration;

/**
 * @description:
 * @author: Fish
 * @date: 2024/5/26
 */
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Connection connection) {
        super(configuration, connection);
    }

    @Override
    protected Object doExec(String methodName, String[] parameterTypes, Object[] args) {
        return connection.execute(methodName, parameterTypes, new String[]{"ignore"}, args);
    }

}
