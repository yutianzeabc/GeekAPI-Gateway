package cc.geektip.gateway.sdk.common;

import lombok.Data;

/**
 * @description: 统一结果封装类
 * @author: Fish
 * @date: 2024/5/30
 */
@Data
public class Result<T> {

    private String code;
    private String info;
    private T data;

}
