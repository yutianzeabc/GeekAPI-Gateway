package cc.geektip.gateway.core.datasource.connection;

import cc.geektip.gateway.core.datasource.Connection;
import org.apache.dubbo.common.config.ReferenceCache;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.SimpleReferenceCache;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @description: Dubbo 连接对象
 * @author: Fish
 * @date: 2024/5/26
 */
public class DubboConnection implements Connection {

    private final GenericService genericService;

    public DubboConnection(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ReferenceConfig<GenericService> reference) {
        // 连接远程服务
        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig).registry(registryConfig).reference(reference).start();
        // 获取泛化接口
        ReferenceCache cache = SimpleReferenceCache.getCache();
        genericService = cache.get(reference);
    }

    @Override
    public Object execute(String method, String[] parameterTypes, String[] parameterNames, Object[] args) {
        return genericService.$invoke(method, parameterTypes, args);
    }

}
