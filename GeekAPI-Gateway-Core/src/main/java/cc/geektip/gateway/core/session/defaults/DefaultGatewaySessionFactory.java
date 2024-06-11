package cc.geektip.gateway.core.session.defaults;

import cc.geektip.gateway.core.datasource.Connection;
import cc.geektip.gateway.core.datasource.DataSource;
import cc.geektip.gateway.core.datasource.DataSourceFactory;
import cc.geektip.gateway.core.datasource.unpooled.UnpooledDataSourceFactory;
import cc.geektip.gateway.core.executor.Executor;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.session.GatewaySession;
import cc.geektip.gateway.core.session.GatewaySessionFactory;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @description: 默认网关会话工厂
 * @author: Fish
 * @date: 2024/5/24
 */

@Slf4j
@Getter
@RequiredArgsConstructor
public class DefaultGatewaySessionFactory implements GatewaySessionFactory {

    private final Configuration configuration;
    private final Cache<String, GatewaySession> sessionCache;
    private final ExecutorService executorService;

    public DefaultGatewaySessionFactory(Configuration configuration) {
        this.configuration = configuration;
        this.sessionCache = Caffeine.newBuilder()
                .expireAfterAccess(15, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public GatewaySession openSession(String uri) {
        // 使用Caffeine缓存来获取会话
        return sessionCache.get(uri, this::createSession);
    }

    private GatewaySession createSession(String uri) {
        // 获取数据源
        DataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration, uri);
        DataSource dataSource = dataSourceFactory.getDataSource();
        // 使用虚拟线程创建会话
        Future<GatewaySession> future = executorService.submit(() -> {
            // 获取连接
            Connection connection = dataSource.getConnection();
            // 创建执行器
            Executor executor = configuration.newExecutor(connection);
            // 创建网关会话
            return new DefaultGatewaySession(configuration, uri, executor);
        });
        try {
            return future.get();
        } catch (Exception e) {
            log.error("Error create session. Cause: ", e);
            throw new RuntimeException("Error create session. Cause: " + e);
        }
    }

}
