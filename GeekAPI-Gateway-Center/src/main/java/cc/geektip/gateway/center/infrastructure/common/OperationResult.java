package cc.geektip.gateway.center.infrastructure.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @description: 运营响应数据封装
 * @author: Fish
 * @date: 2024/6/5
 */
@Data
@AllArgsConstructor
public class OperationResult<T> {

    // 总页数
    private int pageTotal;
    // 数据
    private List<T> list;

}
