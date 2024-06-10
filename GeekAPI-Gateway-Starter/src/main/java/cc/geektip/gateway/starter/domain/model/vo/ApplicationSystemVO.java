package cc.geektip.gateway.starter.domain.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @description: 应用系统 VO
 * @author: Fish
 * @date: 2024/6/1
 */
@Data
public class ApplicationSystemVO {

    /**
     * 系统标识
     */
    private String systemId;
    /**
     * 系统名称
     */
    private String systemName;
    /**
     * 系统类型；RPC、HTTP
     */
    private String systemType;
    /**
     * 注册中心
     */
    private String systemRegistry;
    /**
     * 接口方法
     */
    private List<ApplicationInterfaceVO> interfaceList;

}
