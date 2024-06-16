package cc.geektip.gateway.core.bind;

import cc.geektip.gateway.core.mapping.HttpStatement;
import cc.geektip.gateway.core.session.GatewaySession;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.objectweb.asm.Type;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 统一泛化调用静态代理工厂
 * @author: Fish
 * @date: 2024/5/24
 */
@RequiredArgsConstructor
public class MapperProxyFactory {

    private final String uri;

    private final Cache<String, IGenericReference> genericReferenceCache = Caffeine.newBuilder()
            .expireAfterAccess(6, TimeUnit.HOURS)
            .build();

    public IGenericReference newInstance(GatewaySession gatewaySession) {
        // 使用Caffeine缓存来获取泛化调用服务
        return genericReferenceCache.get(uri, k -> {
            HttpStatement httpStatement = gatewaySession.getConfiguration().getHttpStatement(uri);
            // 泛化调用
            MapperProxy genericReferenceProxy = new MapperProxy(gatewaySession, uri);
            // 创建接口
            InterfaceMaker interfaceMaker = new InterfaceMaker();
            interfaceMaker.add(new Signature(httpStatement.getMethodName(), Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class<?> interfaceClass = interfaceMaker.create();
            // 代理对象
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            // IGenericReference 统一泛化调用接口
            // interfaceClass    根据泛化调用注册信息创建的接口，建立 http -> rpc 关联
            // 通过这种方式，代理对象既可以通过 IGenericReference 接口进行统一的泛化调用，也可以通过动态生成的接口进行特定方法的调用
            enhancer.setInterfaces(new Class[]{IGenericReference.class, interfaceClass});
            enhancer.setCallback(genericReferenceProxy);
            return (IGenericReference) enhancer.create();
        });
    }
}
