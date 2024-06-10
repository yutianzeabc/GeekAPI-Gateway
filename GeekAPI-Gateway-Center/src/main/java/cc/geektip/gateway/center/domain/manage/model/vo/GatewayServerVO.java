package cc.geektip.gateway.center.domain.manage.model.vo;

import lombok.Data;

/**
 * @description: 网关服务配置 VO
 * @author: Fish
 * @date: 2024/5/28
 */
@Data
public class GatewayServerVO {

    /**
     * 分组 ID
     */
    private String groupId;
    /**
     * 分组名称
     */
    private String groupName;

}
