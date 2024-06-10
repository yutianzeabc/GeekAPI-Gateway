package cc.geektip.gateway.center.domain.manage.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 网关分配 VO
 * @author: Fish
 * @date: 2024/6/9
 */
@Data
public class GatewayDistributionVO {

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

}
