package cc.geektip.gateway.center.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName application_interface_method
 */
@TableName(value ="application_interface_method")
@Data
public class ApplicationInterfaceMethod implements Serializable {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

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

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}