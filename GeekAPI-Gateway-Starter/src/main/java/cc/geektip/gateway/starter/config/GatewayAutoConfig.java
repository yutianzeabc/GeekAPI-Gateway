package cc.geektip.gateway.starter.config;

import cc.geektip.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import cc.geektip.gateway.core.socket.GatewaySocketServer;
import cc.geektip.gateway.starter.application.GatewayApplication;
import cc.geektip.gateway.starter.domain.service.GatewayCenterService;
import cc.geektip.gateway.starter.domain.service.HeartbeatPublisher;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description: 网关自动配置类
 * @author: Fish
 * @date: 2024/5/30
 */
@Slf4j
@Configuration
@EnableScheduling
@EnableConfigurationProperties(GatewayServiceProperties.class)
public class GatewayAutoConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService) {
        // 1. 拉取注册中心的 Redis 配置信息
        Map<String, String> redisConfig = gatewayCenterService.queryRedisConfig(properties.getAddress());
        // 2. 构建 Redis 服务
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName(redisConfig.get("host"));
        standaloneConfig.setPort(Integer.parseInt(redisConfig.get("port")));
        standaloneConfig.setDatabase(Integer.parseInt(redisConfig.get("database")));
        standaloneConfig.setPassword(redisConfig.get("password"));
        // 3. 默认配置信息
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxWaitMillis(30 * 1000);
        poolConfig.setMinIdle(20);
        poolConfig.setMaxIdle(40);
        poolConfig.setTestWhileIdle(true);
        // 4. 构建 Redis 配置
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .connectTimeout(Duration.ofSeconds(2))
                .clientName("api-gateway-assist-redis-" + properties.getGatewayId())
                .usePooling().poolConfig(poolConfig).build();
        // 5. 实例化 Redis 链接对象
        return new JedisConnectionFactory(standaloneConfig, clientConfig);
    }

    @Bean
    public RedisMessageListenerContainer container(GatewayServiceProperties properties, RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter msgAgreementListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(msgAgreementListenerAdapter, new PatternTopic(properties.getGatewayId()));
        return container;
    }

    @Bean
    public MessageListenerAdapter msgAgreementListenerAdapter(GatewayApplication gatewayApplication) {
        return new MessageListenerAdapter(gatewayApplication, "receiveSystemUpdate");
    }

    @Bean
    public RedisTemplate<String, String> redisMessageTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public HeartbeatPublisher heartbeatPublisher(RedisTemplate<String, String> redisMessageTemplate, GatewayServiceProperties properties) {
        return new HeartbeatPublisher(redisMessageTemplate, properties);
    }

    @Bean
    public GatewayCenterService registerGatewayService() {
        return new GatewayCenterService();
    }

    @Bean
    public GatewayApplication gatewayApplication(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService, cc.geektip.gateway.core.session.Configuration configuration, HeartbeatPublisher heartbeatPublisher, Channel gatewaySocketServerChannel) {
        return new GatewayApplication(properties, gatewayCenterService, configuration, heartbeatPublisher, gatewaySocketServerChannel);
    }

    /**
     * 创建网关生命周期核心配置：Configuration贯穿整个网关核心通信服务
     *
     * @param properties 网关配置
     * @return Configuration
     */
    @Bean
    public cc.geektip.gateway.core.session.Configuration gatewayCoreConfiguration(GatewayServiceProperties properties) {
        cc.geektip.gateway.core.session.Configuration configuration = new cc.geektip.gateway.core.session.Configuration();
        String[] split = properties.getGatewayAddress().split(":");
        configuration.setHostName(split[0].trim());
        configuration.setPort(Integer.parseInt(split[1].trim()));
        configuration.setBossNThreads(properties.getBossNThreads());
        configuration.setWorkNThreads(properties.getWorkerNThreads());
        return configuration;
    }

    /**
     * 初始化网关服务：创建服务端Channel对象，方便获取和控制网关操作
     *
     * @param configuration 网关配置
     * @return Channel
     */
    @Bean("gatewaySocketServerChannel")
    public Channel initGateway(cc.geektip.gateway.core.session.Configuration configuration) throws ExecutionException, InterruptedException {
        // 1. 基于配置构建会话工厂
        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);
        // 2. 创建启动网关网络服务
        Channel channel;
        GatewaySocketServer server = new GatewaySocketServer(configuration, gatewaySessionFactory);
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Channel> future = executor.submit(server);
            channel = future.get();
            if (null == channel)
                throw new RuntimeException("api gateway core netty server start error channel is null");
            while (!channel.isActive()) {
                log.info("api gateway core netty server gateway starting ...");
                Thread.sleep(500);
            }
        }
        log.info("api gateway core netty server gateway start done! {}", channel.localAddress());
        return channel;
    }

}
