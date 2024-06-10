package cc.geektip.gateway.core.datasource.unpooled;

import cc.geektip.gateway.core.datasource.DataSourceFactory;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.datasource.DataSourceType;
import lombok.Getter;

/**
 * @description: 非池化数据源工厂
 * @author: Fish
 * @date: 2024/5/26
 */
@Getter
public class UnpooledDataSourceFactory implements DataSourceFactory {

    protected UnpooledDataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration, String uri) {
        this.dataSource.setConfiguration(configuration);
        this.dataSource.setDataSourceType(DataSourceType.DUBBO);
        this.dataSource.setHttpStatement(configuration.getHttpStatement(uri));
    }

}
