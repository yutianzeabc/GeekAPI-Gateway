package cc.geektip.gateway.center.domain.operation.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 网关分布数据VO
 * @author: Fish
 * @date: 2024/6/5
 */
@Data
@NoArgsConstructor
public class GatewayDistributionDataVO {

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 分组标识
     */
    private String groupId;

    /**
     * 网关标识
     */
    private String gatewayId;

    /**
     * 系统标识
     */
    private String systemId;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public GatewayDistributionDataVO(String groupId, String gatewayId) {
        this.groupId = groupId;
        this.gatewayId = gatewayId;
    }

}
