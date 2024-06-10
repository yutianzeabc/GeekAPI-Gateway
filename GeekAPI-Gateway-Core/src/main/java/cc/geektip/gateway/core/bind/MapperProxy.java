package cc.geektip.gateway.core.bind;

import cc.geektip.gateway.core.session.GatewaySession;
import lombok.RequiredArgsConstructor;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @description: 统一泛化调用静态代理：做一些拦截处理。为HTTP对应的RPC调用进行代理控制。
 * @author: Fish
 * @date: 2024/5/24
 */
@RequiredArgsConstructor
public class MapperProxy implements MethodInterceptor {

    private final GatewaySession gatewaySession;

    private final String uri;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        MapperMethod linkMethod = new MapperMethod(uri, method, gatewaySession.getConfiguration());
        // 暂时只获取第0个参数
        return linkMethod.execute(gatewaySession, (Map<String, Object>) args[0]);
    }

}
