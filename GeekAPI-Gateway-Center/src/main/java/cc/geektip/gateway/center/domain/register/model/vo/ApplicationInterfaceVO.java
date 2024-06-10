package cc.geektip.gateway.center.domain.register.model.vo;

import lombok.Data;

/**
 * @description: 应用接口 VO
 * @author: Fish
 * @date: 2024/5/29
 */
@Data
public class ApplicationInterfaceVO {

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

}
