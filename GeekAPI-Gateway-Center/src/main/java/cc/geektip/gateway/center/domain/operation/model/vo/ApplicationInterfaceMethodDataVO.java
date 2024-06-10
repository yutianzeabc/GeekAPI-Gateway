package cc.geektip.gateway.center.domain.operation.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 应用接口方法数据VO
 * @author: Fish
 * @date: 2024/6/5
 */
@Data
@NoArgsConstructor
public class ApplicationInterfaceMethodDataVO {

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
     * 参数类型 (RPC 限定单参数注册)
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
     * true = 1是、false = 0否
     */
    private Integer auth;

    public ApplicationInterfaceMethodDataVO(String systemId, String interfaceId) {
        this.systemId = systemId;
        this.interfaceId = interfaceId;
    }


}
