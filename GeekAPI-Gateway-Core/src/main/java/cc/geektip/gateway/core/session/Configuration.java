package cc.geektip.gateway.core.session;

import cc.geektip.gateway.core.authorization.IAuth;
import cc.geektip.gateway.core.authorization.auth.AuthService;
import cc.geektip.gateway.core.bind.IGenericReference;
import cc.geektip.gateway.core.bind.MapperRegistry;
import cc.geektip.gateway.core.datasource.Connection;
import cc.geektip.gateway.core.executor.Executor;
import cc.geektip.gateway.core.executor.SimpleExecutor;
import cc.geektip.gateway.core.mapping.HttpStatement;
import lombok.Data;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description: 会话生命周期配置
 * @author: Fish
 * @date: 2024/5/23
 */
@Data
public class Configuration {

    // 网关生命周期配置更新读写锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    // 映射器注册表
    private final MapperRegistry mapperRegistry = new MapperRegistry(this);
    // HTTP 指令
    private final Map<String, HttpStatement> httpStatements = new ConcurrentHashMap<>();

    // RPC 应用服务配置项
    private final Map<String, ApplicationConfig> applicationConfigMap = new ConcurrentHashMap<>();
    // RPC 注册中心配置项
    private final Map<String, RegistryConfig> registryConfigMap = new ConcurrentHashMap<>();
    // RPC 泛化服务配置项
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new ConcurrentHashMap<>();

    // 鉴权服务
    private final IAuth auth = new AuthService();

    // 网关 Netty 服务地址
    private String hostName = "127.0.0.1";
    // 网关 Netty 服务端口
    private int port = 7397;
    // 网关 Netty 服务线程数配置
    private int bossNThreads = 1;
    private int workNThreads = 4;

    public Configuration() {
    }

    public void requireWLock() {
        lock.writeLock().lock();
    }

    public void releaseWLock() {
        lock.writeLock().unlock();
    }

    public void requireRLock() {
        lock.readLock().lock();
    }

    public void releaseRLock() {
        lock.readLock().unlock();
    }

    public void clear() {
        requireWLock();
        mapperRegistry.clear();
        httpStatements.clear();
        applicationConfigMap.clear();
        registryConfigMap.clear();
        referenceConfigMap.clear();
        releaseWLock();
    }

    public synchronized void registryConfig(String applicationName, String address, String interfaceName, String version) {
        if (applicationConfigMap.get(applicationName) == null) {
            ApplicationConfig application = new ApplicationConfig();
            application.setName(applicationName);
            application.setQosEnable(false);
            applicationConfigMap.put(applicationName, application);
        }

        if (registryConfigMap.get(applicationName) == null) {
            RegistryConfig registry = new RegistryConfig();
            registry.setAddress(address);
            registry.setRegister(false);
            registryConfigMap.put(applicationName, registry);
        }

        if (referenceConfigMap.get(interfaceName) == null) {
            ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
            reference.setInterface(interfaceName);
            reference.setVersion(version);
            reference.setGeneric("true");
            referenceConfigMap.put(interfaceName, reference);
        }
    }

    public ApplicationConfig getApplicationConfig(String applicationName) {
        return applicationConfigMap.get(applicationName);
    }

    public RegistryConfig getRegistryConfig(String applicationName) {
        return registryConfigMap.get(applicationName);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
        return referenceConfigMap.get(interfaceName);
    }

    public void addMapper(HttpStatement httpStatement) {
        mapperRegistry.addMapper(httpStatement);
    }

    public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
        return mapperRegistry.getMapper(uri, gatewaySession);
    }

    public void addHttpStatement(HttpStatement httpStatement) {
        httpStatements.put(httpStatement.getUri(), httpStatement);
    }

    public HttpStatement getHttpStatement(String uri) {
        return httpStatements.get(uri);
    }

    public Executor newExecutor(Connection connection) {
        return new SimpleExecutor(this, connection);
    }

    public boolean authValidate(String uId, String token) {
        return auth.validate(uId, token);
    }

}
