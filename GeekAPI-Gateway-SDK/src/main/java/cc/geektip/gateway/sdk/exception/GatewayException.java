package cc.geektip.gateway.sdk.exception;

/**
 * @description: 网关异常类
 * @author: Fish
 * @date: 2024/6/4
 */
public class GatewayException extends RuntimeException {

    public GatewayException(String msg) {
        super(msg);
    }

    public GatewayException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
