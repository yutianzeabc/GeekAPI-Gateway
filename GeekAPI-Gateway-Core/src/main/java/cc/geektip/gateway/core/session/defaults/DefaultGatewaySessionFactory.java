package cc.geektip.gateway.core.session.defaults;

import cc.geektip.gateway.core.datasource.DataSource;
import cc.geektip.gateway.core.datasource.DataSourceFactory;
import cc.geektip.gateway.core.datasource.unpooled.UnpooledDataSourceFactory;
import cc.geektip.gateway.core.executor.Executor;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.session.GatewaySession;
import cc.geektip.gateway.core.session.GatewaySessionFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 默认网关会话工厂
 * @author: Fish
 * @date: 2024/5/24
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultGatewaySessionFactory implements GatewaySessionFactory {

    private final Configuration configuration;

    @Override
    public GatewaySession openSession(String uri) {
        // 获取数据源
        DataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration, uri);
        DataSource dataSource = dataSourceFactory.getDataSource();
        // 创建执行器
        Executor executor = configuration.newExecutor(dataSource.getConnection());
        // 创建网关会话
        return new DefaultGatewaySession(configuration, uri, executor);
    }

}
