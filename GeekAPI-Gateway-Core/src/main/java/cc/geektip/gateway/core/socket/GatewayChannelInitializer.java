package cc.geektip.gateway.core.socket;

import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import cc.geektip.gateway.core.socket.handlers.AuthorizationHandler;
import cc.geektip.gateway.core.socket.handlers.GatewayServerHandler;
import cc.geektip.gateway.core.socket.handlers.ProtocolDataHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.RequiredArgsConstructor;

/**
 * @description: 会话管道初始化类
 * @author: Fish
 * @date: 2024/5/23
 */
@RequiredArgsConstructor
public class GatewayChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final Configuration configuration;

    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline line = channel.pipeline();
        line.addLast(new HttpRequestDecoder());
        line.addLast(new HttpResponseEncoder());
        line.addLast(new HttpObjectAggregator(1024 * 1024));
        line.addLast(new GatewayServerHandler(configuration));
        line.addLast(new AuthorizationHandler(configuration));
        line.addLast(new ProtocolDataHandler(gatewaySessionFactory));
    }

}
