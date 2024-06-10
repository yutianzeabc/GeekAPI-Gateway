package cc.geektip.gateway.core.socket.agreement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @description: 网关响应结果
 * @author: Fish
 * @date: 2024/5/27
 */
@Getter
@RequiredArgsConstructor
public class GatewayResultMessage {

    private final String code;

    private final String info;

    private final Object data;

    public static GatewayResultMessage buildSuccess(Object data) {
        return new GatewayResultMessage(AgreementConstants.ResponseCode.OK.getCode(), AgreementConstants.ResponseCode.OK.getInfo(), data);
    }

    public static GatewayResultMessage buildError(String code, String info) {
        return new GatewayResultMessage(code, info, null);
    }

}