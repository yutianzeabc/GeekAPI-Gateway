package cc.geektip.gateway.center.domain.register.model.vo;

import lombok.Data;

/**
 * @description: 应用接口方法 VO
 * @author: Fish
 * @date: 2024/5/29
 */
@Data
public class ApplicationInterfaceMethodVO {

    /**
     * 系统标识
     */
    private String systemId;
    /**
     * 接口标识
     */
    private String interfaceId;
    /**
     * 方法标识
     */
    private String methodId;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 参数类型(RPC 限定单参数注册)
     */
    private String parameterType;
    /**
     * 网关接口
     */
    private String uri;
    /**
     * 接口类型；GET、POST、PUT、DELETE
     */
    private String httpCommandType;
    /**
     * 是否鉴权；true = 1、false = 0
     */
    private Integer auth;

}
