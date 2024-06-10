package cc.geektip.gateway.starter.domain.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 应用接口 VO
 * @author: Fish
 * @date: 2024/6/1
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
    /**
     * 方法接口
     */
    private List<ApplicationInterfaceMethodVO> methodList;

}
