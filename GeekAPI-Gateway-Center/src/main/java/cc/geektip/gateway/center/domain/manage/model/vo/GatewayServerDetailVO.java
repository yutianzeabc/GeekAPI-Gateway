package cc.geektip.gateway.center.domain.manage.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 网关服务明细 VO
 * @author: Fish
 * @date: 2024/5/28
 */
@Data
public class GatewayServerDetailVO {

    /**
     * 自增ID
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
     * 网关名称
     */
    private String gatewayName;
    /**
     * 网关地址
     */
    private String gatewayAddress;
    /**
     * 服务状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
