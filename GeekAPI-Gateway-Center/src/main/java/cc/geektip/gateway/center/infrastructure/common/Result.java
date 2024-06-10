package cc.geektip.gateway.center.infrastructure.common;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description: 统一结果返回类
 * @author: Fish
 * @date: 2024/5/28
 */
@Getter
@RequiredArgsConstructor
public class Result<T> implements Serializable {

    private final String code;

    private final String info;

    private final T data;

    @Serial
    private static final long serialVersionUID = 1574302549861223628L;

}
