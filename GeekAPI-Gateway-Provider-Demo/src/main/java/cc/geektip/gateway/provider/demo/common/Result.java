package cc.geektip.gateway.provider.demo.common;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @description: 返回结果封装类
 * @author: Fish
 * @date: 2024/6/12
 */
@Data
@RequiredArgsConstructor
public class Result {

    private final String code;
    private final Object data;
    private final String info;

    public static Result buildSuccess(Object data) {
        return new Result("200", data, "success");
    }

}
