package cc.geektip.gateway.core.socket;

import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @description: 网关会话服务
 * @author: Fish
 * @date: 2024/5/23
 */
@Slf4j
public class GatewaySocketServer implements Callable<Channel> {

    private final Configuration configuration;
    private final DefaultGatewaySessionFactory gatewaySessionFactory;
    private EventLoopGroup boss;
    private EventLoopGroup work;
    private Channel channel;

    public GatewaySocketServer(Configuration configuration, DefaultGatewaySessionFactory gatewaySessionFactory) {
        this.configuration = configuration;
        this.gatewaySessionFactory = gatewaySessionFactory;
        this.initEventLoopGroup();
    }

    private void initEventLoopGroup(){
        boss = new NioEventLoopGroup(configuration.getBossNThreads());
        work = new NioEventLoopGroup(configuration.getWorkNThreads());
    }

    @Override
    public Channel call() throws Exception {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new GatewayChannelInitializer(configuration, gatewaySessionFactory));

            channelFuture = b.bind(configuration.getPort()).syncUninterruptibly();
            this.channel = channelFuture.channel();
        } catch (Exception e) {
            log.error("Socket server start failed: ", e);
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                log.info("Socket server start done!");
            } else {
                log.error("Socket server start failed!");
            }
        }
        return channel;
    }

}
