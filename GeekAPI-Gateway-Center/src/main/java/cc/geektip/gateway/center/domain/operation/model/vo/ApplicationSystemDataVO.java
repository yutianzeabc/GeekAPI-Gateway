package cc.geektip.gateway.center.domain.operation.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 应用系统数据VO
 * @author: Fish
 * @date: 2024/6/5
 */
@Data
@NoArgsConstructor
public class ApplicationSystemDataVO {

    /**
     * 系统标识
     */
    private String systemId;

    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 系统类型：RPC、HTTP
     */
    private String systemType;

    /**
     * 注册中心
     */
    private String systemRegistry;

    public ApplicationSystemDataVO(String systemId, String systemName) {
        this.systemId = systemId;
        this.systemName = systemName;
    }

}
