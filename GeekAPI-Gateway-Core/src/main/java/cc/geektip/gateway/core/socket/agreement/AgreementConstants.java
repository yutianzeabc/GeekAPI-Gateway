package cc.geektip.gateway.core.socket.agreement;

import cc.geektip.gateway.core.mapping.HttpStatement;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @description: 协议常量
 * @author: Fish
 * @date: 2024/5/27
 */
public class AgreementConstants {

    public static final AttributeKey<HttpStatement> HTTP_STATEMENT = AttributeKey.valueOf("HttpStatement");

    @Getter
    @RequiredArgsConstructor
    public enum ResponseCode {
        OK("200", "成功"),
        BAD_REQUEST("400", "请求参数错误"),
        FORBIDDEN("403", "请求被拒绝"),
        NOT_FOUND("404", "请求目标不存在"),
        INTERNAL_ERROR("500", "服务器内部错误"),
        BAD_GATEWAY("502", "服务器作为网关或代理，从上游服务器收到无效响应");

        private final String code;
        private final String info;
    }
}
