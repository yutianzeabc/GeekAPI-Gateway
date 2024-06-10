package cc.geektip.gateway.core.socket.handlers;

import cc.geektip.gateway.core.bind.IGenericReference;
import cc.geektip.gateway.core.executor.result.SessionResult;
import cc.geektip.gateway.core.session.GatewaySession;
import cc.geektip.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import cc.geektip.gateway.core.socket.BaseHandler;
import cc.geektip.gateway.core.socket.agreement.AgreementConstants;
import cc.geektip.gateway.core.socket.agreement.RequestParser;
import cc.geektip.gateway.core.socket.agreement.ResponseParser;
import cc.geektip.gateway.core.socket.agreement.GatewayResultMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @description: 协议数据处理器
 * @author: Fish
 * @date: 2024/5/27
 */
@Slf4j
@RequiredArgsConstructor
public class ProtocolDataHandler extends BaseHandler<FullHttpRequest> {

    private final DefaultGatewaySessionFactory gatewaySessionFactory;

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        log.info("网关接收请求【消息】 URI={} Method={}", request.uri(), request.method());
        try {
            // 1. 解析请求参数
            RequestParser requestParser = new RequestParser(request);
            String uri = requestParser.getUri();
            if (null == uri) return;
            Map<String, Object> args = requestParser.parse();
            // 2. 调用会话服务
            GatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
            IGenericReference reference = gatewaySession.getMapper();
            SessionResult result = reference.$invoke(args);
            // 3. 封装返回结果
            DefaultFullHttpResponse response = new ResponseParser().parse("0000".equals(result.getCode()) ? GatewayResultMessage.buildSuccess(result.getData()) : GatewayResultMessage.buildError(AgreementConstants.ResponseCode.NOT_FOUND.getCode(), "网关协议调用失败！"));
            channel.writeAndFlush(response);
        } catch (Exception e) {
            DefaultFullHttpResponse response = new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode.BAD_GATEWAY.getCode(), "网关协议调用失败！" + e.getMessage()));
            channel.writeAndFlush(response);
        }
    }

}
