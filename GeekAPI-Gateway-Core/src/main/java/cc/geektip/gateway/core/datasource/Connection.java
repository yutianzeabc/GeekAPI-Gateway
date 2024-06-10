package cc.geektip.gateway.core.datasource;

/**
 * @description: 数据源连接接口
 * @author: Fish
 * @date: 2024/5/26
 */
public interface Connection {
    /**
     * 执行方法
     * @param method 方法名
     * @param parameterTypes 参数类型
     * @param parameterNames 参数名
     * @param args 参数
     * @return 执行结果
     */
    Object execute(String method, String[] parameterTypes, String[] parameterNames, Object[] args);

}
