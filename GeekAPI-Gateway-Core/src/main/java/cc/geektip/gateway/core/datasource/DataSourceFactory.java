package cc.geektip.gateway.core.datasource;

import cc.geektip.gateway.core.session.Configuration;

/**
 * @description: 数据源工厂
 * @author: Fish
 * @date: 2024/5/26
 */
public interface DataSourceFactory {

    /**
     * 获取数据源
     * @param configuration 会话生命周期配置
     * @param uri 数据源 URI
     */
    void setProperties(Configuration configuration, String uri);

    /**
     * 获取数据源
     * @return 数据源
     */
    DataSource getDataSource();

}
