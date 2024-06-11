package cc.geektip.gateway.core.socket.agreement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @description: 网关响应结果
 * @author: Fish
 * @date: 2024/5/27
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GatewayResultMessage {

    private final String code;
    private final String info;
    private final Object data;
    private String node;

    public static GatewayResultMessage buildSuccess(Object data) {
        return new GatewayResultMessage(AgreementConstants.ResponseCode.OK.getCode(), AgreementConstants.ResponseCode.OK.getInfo(), data);
    }

    public static GatewayResultMessage buildSuccess(Object data, String node) {
        return new GatewayResultMessage(AgreementConstants.ResponseCode.OK.getCode(), AgreementConstants.ResponseCode.OK.getInfo(), data, node);
    }

    public static GatewayResultMessage buildError(String code, String info) {
        return new GatewayResultMessage(code, info, null);
    }

    public static GatewayResultMessage buildError(String code, String info, String node) {
        return new GatewayResultMessage(code, info, null, node);
    }

}