package cc.geektip.gateway.core.datasource;

/**
 * @description: 数据源接口，RPC、HTTP 都当做连接的数据资源使用
 * @author: Fish
 * @date: 2024/5/26
 */
public interface DataSource {

    /**
     * 获取连接对象
     * @return 连接对象
     */
    Connection getConnection();

}
