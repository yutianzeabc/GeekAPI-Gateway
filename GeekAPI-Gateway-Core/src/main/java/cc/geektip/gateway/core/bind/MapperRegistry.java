package cc.geektip.gateway.core.bind;

import cc.geektip.gateway.core.mapping.HttpStatement;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.session.GatewaySession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 统一泛化调用注册表
 * @author: Fish
 * @date: 2024/5/24
 */
@Slf4j
@RequiredArgsConstructor
public class MapperRegistry {
    /**
     * 会话配置
     */
    private final Configuration configuration;

    /**
     * 泛化调用静态代理工厂
     */
    private final Map<String, MapperProxyFactory> knownMappers = new ConcurrentHashMap<>();

    /**
     * 获取泛化调用服务
     *
     * @param uri            服务URI
     * @param gatewaySession 网关会话
     * @return 泛化调用服务
     */
    public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
        final MapperProxyFactory mapperProxyFactory = knownMappers.get(uri);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Uri " + uri + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(gatewaySession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    /**
     * 添加映射
     *
     * @param httpStatement HTTP语句
     */
    public void addMapper(HttpStatement httpStatement) {
        String uri = httpStatement.getUri();
        // 如果重复注册，更新
        if (hasMapper(uri)) {
            log.info("Update mapper for uri: {}", uri);
        } else {
            log.info("Add mapper for uri: {}", uri);
            knownMappers.put(uri, new MapperProxyFactory(uri));
        }
        // 保存接口映射信息
        configuration.addHttpStatement(httpStatement);
    }

    public <T> boolean hasMapper(String uri) {
        return knownMappers.containsKey(uri);
    }

}
