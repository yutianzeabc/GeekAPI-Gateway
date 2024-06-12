package cc.geektip.gateway.core.socket.handlers;

import cc.geektip.gateway.core.mapping.HttpStatement;
import cc.geektip.gateway.core.session.Configuration;
import cc.geektip.gateway.core.socket.BaseHandler;
import cc.geektip.gateway.core.socket.agreement.AgreementConstants;
import cc.geektip.gateway.core.socket.agreement.GatewayResultMessage;
import cc.geektip.gateway.core.socket.agreement.ResponseParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 鉴权处理器
 * @author: Fish
 * @date: 2024/5/27
 */
@Slf4j
@RequiredArgsConstructor
public class AuthorizationHandler extends BaseHandler<FullHttpRequest> {

    private final Configuration configuration;

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        log.info("网关接收请求【鉴权】 URI={} Method={}", request.uri(), request.method());
        try {
            HttpStatement httpStatement = channel.attr(AgreementConstants.HTTP_STATEMENT).get();
            if (httpStatement.isAuth()) {
                try {
                    // 鉴权信息
                    String uId = request.headers().get("uId");
                    String token = request.headers().get("token");
                    // 鉴权判断
                    if (null == token || token.isEmpty()) {
                        channel.writeAndFlush(new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode.BAD_REQUEST.getCode(), "无效Token！")));
                    }
                    // 鉴权处理
                    boolean status = configuration.authValidate(uId, token);
                    // 鉴权成功
                    if (status) {
                        request.retain();
                        ctx.fireChannelRead(request);
                    }
                    // 鉴权失败
                    else {
                        channel.writeAndFlush(new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode.FORBIDDEN.getCode(), "无权访问！")));
                    }
                } catch (Exception e) {
                    channel.writeAndFlush(new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode.FORBIDDEN.getCode(), "鉴权失败！")));
                }
            }
            // 无需鉴权
            else {
                request.retain();
                ctx.fireChannelRead(request);
            }
        } catch (Exception e) {
            log.error("网关协议调用失败！{}", e.getMessage(), e);
            channel.writeAndFlush(new ResponseParser().parse(GatewayResultMessage.buildError(AgreementConstants.ResponseCode.INTERNAL_ERROR.getCode(), "网关协议调用失败！" + e.getMessage())));
        }
    }

}
