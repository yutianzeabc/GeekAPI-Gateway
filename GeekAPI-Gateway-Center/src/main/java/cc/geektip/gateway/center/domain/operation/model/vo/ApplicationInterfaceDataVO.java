package cc.geektip.gateway.center.domain.operation.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 应用接口数据VO
 * @author: Fish
 * @date: 2024/6/5
 */
@Data
@NoArgsConstructor
public class ApplicationInterfaceDataVO {

    /**
     * 系统标识
     */
    private String systemId;

    /**
     * 接口标识
     */
    private String interfaceId;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口版本
     */
    private String interfaceVersion;

    public ApplicationInterfaceDataVO(String systemId, String interfaceId) {
        this.systemId = systemId;
        this.interfaceId = interfaceId;
    }

}
