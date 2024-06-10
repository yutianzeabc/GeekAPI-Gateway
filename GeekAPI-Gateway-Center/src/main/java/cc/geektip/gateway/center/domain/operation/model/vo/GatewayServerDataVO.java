package cc.geektip.gateway.center.domain.operation.model.vo;

import lombok.Data;

/**
 * @description: 网关算力数据VO
 * @author: Fish
 * @date: 2024/6/5
 */
@Data
public class GatewayServerDataVO {

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 分组标识
     */
    private String groupId;

    /**
     * 分组名称
     */
    private String groupName;

}
