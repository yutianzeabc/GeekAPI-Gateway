package cc.geektip.gateway.test;

import cc.geektip.gateway.core.mapping.HttpCommandType;
import cc.geektip.gateway.core.mapping.HttpStatement;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import cc.geektip.gateway.core.socket.GatewaySocketServer;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @description: API测试类
 * @author: Fish
 * @date: 2024/5/24
 */
@Slf4j
public class ApiTest {
    /**
     * 测试：
     * http://localhost:7397/wg/activity/sayHi
     * 参数：
     * {
     * "str": "10001"
     * }
     * <p>
     * http://localhost:7397/wg/activity/insert
     * 参数：
     * {
     * "name":"Fish",
     * "uid":"10001"
     * }
     */
    @Test
    public void test_gateway() throws InterruptedException, ExecutionException {
        // 1. 创建配置信息加载注册
        Configuration configuration = new Configuration();
        configuration.setHostName("127.0.0.1");
        configuration.setPort(7397);

        // 2. 基于配置构建会话工厂
        DefaultGatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);

        // 3. 创建启动网关网络服务
        GatewaySocketServer server = new GatewaySocketServer(configuration, gatewaySessionFactory);

        Future<Channel> future = Executors.newFixedThreadPool(2).submit(server);
        Channel channel = future.get();

        if (null == channel) throw new RuntimeException("netty server start error channel is null");

        while (!channel.isActive()) {
            log.info("Netty server gateway starting ...");
            Thread.sleep(500);
        }
        log.info("Netty server gateway start done! {}", channel.localAddress());

        HttpStatement httpStatement01 = new HttpStatement(
                "api-gateway-test",
                "cc.geektip.gateway.rpc.IActivityBooth",
                "sayHi",
                "java.lang.String",
                "/wg/activity/sayHi",
                HttpCommandType.GET,
                false);

        HttpStatement httpStatement02 = new HttpStatement(
                "api-gateway-test",
                "cc.geektip.gateway.rpc.IActivityBooth",
                "insert",
                "cc.geektip.gateway.rpc.dto.XReq",
                "/wg/activity/insert",
                HttpCommandType.POST,
                true);

        configuration.addMapper(httpStatement01);
        configuration.addMapper(httpStatement02);

        Thread.sleep(Long.MAX_VALUE);
    }

}
