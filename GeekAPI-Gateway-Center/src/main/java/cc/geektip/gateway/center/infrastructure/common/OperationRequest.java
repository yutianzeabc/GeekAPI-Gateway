package cc.geektip.gateway.center.infrastructure.common;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @description: 运营请求参数封装类
 * @author: Fish
 * @date: 2024/6/5
 */
@Data
public class OperationRequest<T> {

    // 起始 Limit
    private int pageStart;
    // 结束 Limit
    private int pageEnd;
    // 页数
    private int pageIndex;
    // 行数
    private int pageSize;

    // 数据
    private T data;

    public OperationRequest(String page, String rows) {
        setPage(page, rows);
    }

    public OperationRequest(int page, int rows) {
        this.pageIndex = 0 == page ? 1 : page;
        this.pageSize = 0 == rows ? 10 : rows;
        this.pageStart = (this.pageIndex - 1) * this.pageSize;
        this.pageEnd = this.pageSize;
    }

    public void setPage(String page, String rows) {
        this.pageIndex = StringUtils.hasText(page) ? Integer.parseInt(page) : 1;
        this.pageSize = StringUtils.hasText(page) ? Integer.parseInt(rows) : 10;
        if (this.pageIndex == 0) {
            this.pageIndex = 1;
        }
        this.pageStart = (this.pageIndex - 1) * this.pageSize;
        this.pageEnd = this.pageSize;
    }

    public void setData(T data) {
        if (data instanceof String str) {
            if (!StringUtils.hasText(str)) {
                data = null;
            }
        }
        this.data = data;
    }
}
