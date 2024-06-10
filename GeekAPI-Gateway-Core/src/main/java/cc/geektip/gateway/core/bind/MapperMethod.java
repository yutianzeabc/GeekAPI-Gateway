package cc.geektip.gateway.core.bind;

import cc.geektip.gateway.core.mapping.HttpCommandType;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.session.GatewaySession;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @description: 统一泛化调用方法绑定
 * @author: Fish
 * @date: 2024/5/25
 */
@RequiredArgsConstructor
public class MapperMethod {

    private final String methodName;

    private final HttpCommandType command;

    public MapperMethod(String uri, Method method, Configuration configuration) {
        this.methodName = configuration.getHttpStatement(uri).getMethodName();
        this.command = configuration.getHttpStatement(uri).getHttpCommandType();
    }

    public Object execute(GatewaySession session, Map<String, Object> params) {
        Object result = null;
        switch (command) {
            case GET:
                result = session.get(methodName, params);
                break;
            case POST:
                result = session.post(methodName, params);
                break;
            case PUT:
                break;
            case DELETE:
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + command);
        }
        return result;
    }

}
