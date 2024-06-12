package cc.geektip.gateway.core.socket.handlers;

import cc.geektip.gateway.core.mapping.HttpStatement;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.socket.BaseHandler;
import cc.geektip.gateway.core.socket.agreement.AgreementConstants;
import cc.geektip.gateway.core.socket.agreement.GatewayResultMessage;
import cc.geektip.gateway.core.socket.agreement.RequestParser;
import cc.geektip.gateway.core.socket.agreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 网关服务处理器
 * @author: Fish
 * @date: 2024/5/24
 */
@Slf4j
@RequiredArgsConstructor
public class GatewayServerHandler extends BaseHandler<FullHttpRequest> {

    private final Configuration configuration;

    @Override
    protected void session(ChannelHandlerContext ctx, final Channel channel, FullHttpRequest request) {
        log.info("网关接收请求【全局】 URI={} Method={}", request.uri(), request.method());
        try {
            // 1. 解析参数
            RequestParser requestParser = new RequestParser(request);
            String uri = requestParser.getUri();
            // 2. 保存信息：HttpStatement
            HttpStatement httpStatement = configuration.getHttpStatement(uri);
            if (null == httpStatement) {
                DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode.NOT_FOUND.getCode(), AgreementConstants.ResponseCode.NOT_FOUND.getInfo()));
                channel.writeAndFlush(response);
                return;
            }
            channel.attr(AgreementConstants.HTTP_STATEMENT).set(httpStatement);
            // 3. 放行服务
            request.retain();
            ctx.fireChannelRead(request);
        } catch (Exception e) {
            DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode.INTERNAL_ERROR.getCode(), "网关协议调用失败！" + e.getMessage()));
            channel.writeAndFlush(response);
        }
    }

}