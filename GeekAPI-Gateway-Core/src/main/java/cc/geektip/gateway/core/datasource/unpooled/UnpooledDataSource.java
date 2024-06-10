package cc.geektip.gateway.core.datasource.unpooled;

import cc.geektip.gateway.core.datasource.Connection;
import cc.geektip.gateway.core.datasource.DataSource;
import cc.geektip.gateway.core.datasource.connection.DubboConnection;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.datasource.DataSourceType;
import cc.geektip.gateway.core.mapping.HttpStatement;
import lombok.Data;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @description: 非池化数据源
 * @author: Fish
 * @date: 2024/5/26
 */
@Data
public class UnpooledDataSource implements DataSource {

    private Configuration configuration;
    private HttpStatement httpStatement;
    private DataSourceType dataSourceType;

    @Override
    public Connection getConnection() {
        switch (dataSourceType) {
            case HTTP:
                // TODO 预留
                break;
            case DUBBO:
                // 配置信息
                String application = httpStatement.getApplication();
                String interfaceName = httpStatement.getInterfaceName();
                // 获取服务
                ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
                RegistryConfig registryConfig = configuration.getRegistryConfig(application);
                ReferenceConfig<GenericService> reference = configuration.getReferenceConfig(interfaceName);
                return new DubboConnection(applicationConfig, registryConfig, reference);
            default:
                break;
        }
        throw new RuntimeException("DataSourceType：" + dataSourceType + "没有对应的数据源实现");
    }

}
