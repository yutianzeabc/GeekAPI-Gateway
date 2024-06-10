package cc.geektip.gateway.core.executor.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @description: 会话响应结果
 * @author: Fish
 * @date: 2024/5/26
 */
@Getter
@RequiredArgsConstructor
public class SessionResult {

    private final String code;

    private final String info;

    private final Object data;

    public static SessionResult buildSuccess(Object data){
        return new SessionResult("0000","Call Success", data);
    }

    public static SessionResult buildError(Object data){
        return new SessionResult("0001","Call Failed", data);
    }

}
