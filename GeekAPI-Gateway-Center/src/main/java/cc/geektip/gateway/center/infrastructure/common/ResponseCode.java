package cc.geektip.gateway.center.infrastructure.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @description: 响应码枚举类
 * @author: Fish
 * @date: 2024/5/28
 */
@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    SUCCESS("0000", "成功"),

    UNKNOWN_ERROR("0001", "未知失败"),

    ILLEGAL_PARAMETER("0002", "非法参数"),

    INDEX_DUP("0003", "主键冲突"),

    NO_UPDATE("0004", "SQL操作无更新");

    private final String code;

    private final String info;

}
